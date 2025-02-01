package org.loan.loanservice.service;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.loan.loanservice.exception.*;
import org.loan.loanservice.model.dto.PaidInstallment;
import org.loan.loanservice.model.entity.Customer;
import org.loan.loanservice.model.entity.Loan;
import org.loan.loanservice.model.entity.LoanInstalment;
import org.loan.loanservice.model.enums.NumberOfInstallment;
import org.loan.loanservice.model.response.CreateLoanResponse;
import org.loan.loanservice.model.response.PayLoanResponse;
import org.loan.loanservice.repository.CustomerRepository;
import org.loan.loanservice.repository.LoanRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentServiceImpl implements PaymentService {

    private final LoanRepository loanRepository;
    private final CustomerRepository customerRepository;

    @Override
    public CreateLoanResponse requestLoan(Customer customer, long amount, Integer numberOfInstallment) {
        Optional<Loan> openLoan = loanRepository.findByisPaidFalse();
        openLoan.ifPresent(t -> {
            throw new LoanAlreadyExistException();
        });
        validateCredit(customer, amount);
        int period = NumberOfInstallment.fromValue(numberOfInstallment);
        BigDecimal interest = new BigDecimal("0.1");
        BigDecimal loanAmount = BigDecimal.valueOf(amount).
                multiply(BigDecimal.ONE.add(interest)).setScale(2, RoundingMode.HALF_UP);
        LocalDate dueDate = getDueDate();
        List<LoanInstalment> installmentList = new ArrayList<>();
        Loan newLoan = new Loan();
        newLoan.setLoanAmount(loanAmount);
        newLoan.setCreateDate(LocalDate.now());
        newLoan.setCustomerId(customer.getId());
        newLoan.setPaid(false);
        newLoan.setNumberOfInstallments(period);
        newLoan.setInstallment(installmentList);
        BigDecimal installmentAmount = loanAmount.divide(BigDecimal.valueOf(period), RoundingMode.HALF_DOWN).setScale(2, RoundingMode.HALF_UP);
        for (int i = 0; i < period; i++) {
            LoanInstalment installment = new LoanInstalment();
            installment.setAmount(installmentAmount);
            installment.setPaid(false);
            installment.setPaidAmount(BigDecimal.ZERO);
            installment.setDueDate(dueDate.plusMonths(i));
            newLoan.addLoan(installment);
        }
        Loan savedLoan = loanRepository.save(newLoan);
        customer.setUsedCreditLimit(customer.getUsedCreditLimit().add(newLoan.getLoanAmount()));
        customerRepository.save(customer);
        log.debug("Saved loan: {}", savedLoan);
        return CreateLoanResponse.builder()
                .loanId(savedLoan.getId())
                .installments(savedLoan.getInstallment()).build();

    }

    @Override
    @Transactional
    public PayLoanResponse payLoan(Customer customer, int loanId, long amount) {
        Loan customerLoan = loanRepository.findCustomerLoan(customer.getId(), loanId);
        if (customerLoan == null)
            throw new LoanDoesNotExistException();
        if (customerLoan.isPaid())
            throw new LoanIsClosedException();
        if (amount <= 0) {
            throw new AmountException();
        }
        List<PaidInstallment> paidInstallments = new ArrayList<>();
        PayLoanResponse payLoanResponse = PayLoanResponse.builder()
                .loanId(loanId)
                .loanInstalment(paidInstallments).build();
        LocalDate lastPaymnetDate = LocalDate.now().plusMonths(3).minusDays(LocalDate.now().getDayOfMonth()).plusDays(1);
        BigDecimal paymentAmount = new BigDecimal(amount);

        for (LoanInstalment installment : customerLoan.getInstallment()) {
            if (installment.getDueDate().isAfter(lastPaymnetDate) || installment.getDueDate().isBefore(lastPaymnetDate)) {
                if (installment.isPaid())
                    continue;
                BigDecimal penaltyOrRewardAmount = getPenaltyOrRewardAmount(installment, paymentAmount);
                BigDecimal depth = installment.getAmount().subtract(installment.getPaidAmount()).add(penaltyOrRewardAmount);
                if (paymentAmount.compareTo(depth) < 0) {
                    installment.setPaidAmount(installment.getPaidAmount().add(paymentAmount));
                    installment.setPaymentDate(LocalDate.now());
                    paidInstallments.add(PaidInstallment.builder()
                            .id(installment.getId())
                            .dueDate(installment.getDueDate())
                            .paidAmount(installment.getPaidAmount()).build());
                    break;
                } else {
                    paymentAmount = paymentAmount.subtract(depth);
                    installment.setPaidAmount(installment.getPaidAmount().add(depth));
                    installment.setPaymentDate(LocalDate.now());
                    installment.setPaid(true);
                    paidInstallments.add(PaidInstallment.builder()
                            .id(installment.getId())
                            .dueDate(installment.getDueDate())
                            .paidAmount(installment.getPaidAmount()).build());
                }
            }
        }

        if (!customerLoan.getInstallment().isEmpty()
                && customerLoan.getInstallment().stream().allMatch(LoanInstalment::isPaid))
            customerLoan.setPaid(true);
        loanRepository.save(customerLoan);

        return payLoanResponse;
    }

    private BigDecimal getPenaltyOrRewardAmount(LoanInstalment installment, BigDecimal paymentAmount) {
        LocalDate today = LocalDate.now();
        if (installment.getDueDate().isAfter(today)) {
            long diffInDays = today.until(installment.getDueDate(), ChronoUnit.DAYS);
            BigDecimal multiply = new BigDecimal("0.001").multiply(new BigDecimal(diffInDays));
            return new BigDecimal(installment.getAmount().add(multiply).longValue()).setScale(2, RoundingMode.HALF_UP);
        } else if (installment.getDueDate().isBefore(today)) {
            long diffInDays = installment.getDueDate().until(today, ChronoUnit.DAYS);
            BigDecimal multiply = new BigDecimal("0.001").multiply(new BigDecimal(diffInDays));
            return new BigDecimal(installment.getAmount().subtract(multiply).longValue()).setScale(2, RoundingMode.HALF_UP);
        } else {
            return BigDecimal.ZERO;
        }

    }

    private LocalDate getDueDate() {
        LocalDate nextMonth = LocalDate.now().plusMonths(1);
        int remainingDay = nextMonth.getDayOfMonth();
        if (remainingDay > 1) {
            return nextMonth.minusDays(nextMonth.getDayOfMonth() + 1);
        }
        return nextMonth.plusMonths(1);
    }

    private void validateCredit(Customer customer, long amount) {
        if (customer.getCreditLimit().compareTo(BigDecimal.ZERO) <= 0)
            throw new CreditInsufficentException();
        if (amount == 0)
            throw new AmountException();
        if (customer.getUsedCreditLimit().add(BigDecimal.valueOf(amount)).compareTo(customer.getCreditLimit()) > 0)
            throw new CreditInsufficentException();
    }
}