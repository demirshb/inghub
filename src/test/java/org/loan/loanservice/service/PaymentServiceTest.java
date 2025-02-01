package org.loan.loanservice.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.loan.loanservice.model.entity.Customer;
import org.loan.loanservice.model.entity.Loan;
import org.loan.loanservice.model.entity.LoanInstalment;
import org.loan.loanservice.model.response.CreateLoanResponse;
import org.loan.loanservice.repository.CustomerRepository;
import org.loan.loanservice.repository.LoanRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class PaymentServiceTest {
    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private LoanRepository loanRepository;

    @InjectMocks
    private PaymentServiceImpl paymentService;

    private Customer customer;
    private Loan loan;

    @BeforeEach
    public void setup() {
        customer = Customer.builder()
                .id(1)
                .name("test")
                .surname("test")
                .email("test@test.com")
                .creditLimit(new BigDecimal("10000000"))
                .usedCreditLimit(new BigDecimal(550000))
                .build();

        loan = new Loan();
        loan.setId(1);
        loan.setLoanAmount(BigDecimal.valueOf(550000));
        loan.setPaid(false);
        loan.setCustomerId(customer.getId());
        loan.setNumberOfInstallments(6);
        loan.setCreateDate(LocalDate.now());
        List<LoanInstalment> installmentList = new ArrayList<>();
        LocalDate nextMonth = LocalDate.now().plusMonths(1);
        int remainingDay = nextMonth.getDayOfMonth();
        for (int i = 0; i < 6; i++) {
            LocalDate dueDate;
            if (remainingDay > 1) {
                dueDate = nextMonth.minusDays(nextMonth.getDayOfMonth() + 1);
            } else {
                dueDate = nextMonth.plusMonths(1);
            }
            LoanInstalment loanInstalment = new LoanInstalment();
            loanInstalment.setId(i + 1);
            loanInstalment.setDueDate(dueDate.plusMonths(i));
            loanInstalment.setLoan(loan);
            loanInstalment.setAmount(new BigDecimal("91666.67"));
            installmentList.add(loanInstalment);
        }
        loan.setInstallment(installmentList);

    }

    @DisplayName("JUnit test for create loan")
    @Test
    public void givenEmployeeObject_whenSaveEmployee_thenReturnEmployeeObject() {
        given(loanRepository.save(any())).willReturn(loan);
        CreateLoanResponse resp = paymentService.requestLoan(customer, 500000, 6);
        assertThat(resp).isNotNull();
        assertThat(resp.getInstallments().isEmpty()).isEqualTo(false);
        assertThat(resp.getInstallments().size()).isEqualTo(6);
    }
}
