package org.loan.loanservice.service.impl;


import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.loan.loanservice.exception.LoanDoesNotExistException;
import org.loan.loanservice.exception.UserDoesNotExistException;
import org.loan.loanservice.model.entity.Customer;
import org.loan.loanservice.model.entity.Loan;
import org.loan.loanservice.model.entity.LoanInstalment;
import org.loan.loanservice.model.enums.Role;
import org.loan.loanservice.model.response.CreateLoanResponse;
import org.loan.loanservice.model.response.PayLoanResponse;
import org.loan.loanservice.repository.CustomerRepository;
import org.loan.loanservice.repository.LoanRepository;
import org.loan.loanservice.service.PaymentService;
import org.loan.loanservice.service.UserService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final CustomerRepository customerRepository;
    private final PaymentService paymentService;
    private final LoanRepository loanRepository;

    @Override
    public UserDetailsService userDetailsService() {
        return username -> customerRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Override
    public CreateLoanResponse createLoan(Integer customerId, long amount, Integer numberOfInstallment) {
        Optional<Customer> customer = customerRepository.findById(customerId);
        if (customer.isEmpty())
            throw new UserDoesNotExistException();
        return paymentService.requestLoan(customer.get(), amount, numberOfInstallment);
    }

    @Override
    public PayLoanResponse payLoan(Integer customerId, int loanId, long amount) {
        Optional<Customer> customer = customerRepository.findById(customerId);
        if (customer.isEmpty())
            throw new UserDoesNotExistException();

        return paymentService.payLoan(customer.get(), loanId, amount);
    }

    @Override
    public List<Loan> listLoan(Integer customerId) {
        return loanRepository.findByCustomerId(customerId);
    }

    @Override
    public List<LoanInstalment> listLoanInstallments(Integer customerId, int loanId) {
        Loan customerLoan = loanRepository.findCustomerLoan(customerId, loanId);
        if (customerLoan != null)
            return customerLoan.getInstallment();
        throw new LoanDoesNotExistException();
    }

    @PostConstruct
    private void postConstruct() {
        Customer user = new Customer();

        user.setName("admin");
        user.setSurname("admin");
        user.setEmail("admin@abc.com");
        user.setCreditLimit(new BigDecimal("1000000"));
        user.setUsedCreditLimit(BigDecimal.ZERO);
        user.setPassword(new BCryptPasswordEncoder().encode("admin"));
        user.setRole(Role.ADMIN);

        customerRepository.save(user);
    }
}