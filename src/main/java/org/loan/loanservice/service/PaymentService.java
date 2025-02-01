package org.loan.loanservice.service;

import org.loan.loanservice.model.entity.Customer;
import org.loan.loanservice.model.response.CreateLoanResponse;
import org.loan.loanservice.model.response.PayLoanResponse;

public interface PaymentService {

    CreateLoanResponse requestLoan(Customer customer, long amount, Integer numberOfInstallment);

    PayLoanResponse payLoan(Customer customer, int loanId, long amount);
}
