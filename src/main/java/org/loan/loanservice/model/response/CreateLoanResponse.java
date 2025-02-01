package org.loan.loanservice.model.response;

import lombok.Builder;
import lombok.Data;
import org.loan.loanservice.model.entity.LoanInstalment;

import java.util.List;

@Builder
@Data
public class CreateLoanResponse {
    private Integer loanId;
    private List<LoanInstalment> installments;
}