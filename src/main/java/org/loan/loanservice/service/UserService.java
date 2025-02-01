package org.loan.loanservice.service;

import org.loan.loanservice.model.entity.Loan;
import org.loan.loanservice.model.entity.LoanInstalment;
import org.loan.loanservice.model.response.CreateLoanResponse;
import org.loan.loanservice.model.response.PayLoanResponse;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService {
    UserDetailsService userDetailsService();

    CreateLoanResponse createLoan(Integer customerId, long loanAmount, Integer numberOfInstallment);

    PayLoanResponse payLoan(Integer customerId, int loanId, long amount);

    List<Loan> listLoan(Integer id);

    List<LoanInstalment> listLoanInstallments(Integer customerId, int loanId);
}
