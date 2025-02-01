package org.loan.loanservice.model.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LoanRequestUser {
    @NotNull(message = "Amount must not be null")
    @Min(value = 0, message = "amount must be greater than 100")
    private Long amount;
    @NotNull(message = "Number of installment must not be null")
    private Integer numberOfInstallment;
}
