package org.loan.loanservice.exception;

import lombok.Getter;
import org.loan.loanservice.config.Constants;

@Getter
public class LoanAlreadyExistException extends ApiException {
    private final int code;

    public LoanAlreadyExistException() {
        super(Constants.ErrorMessage.LOAN_EXIST);
        this.code = Constants.ErrorCode.LOAN_EXIST;
    }
}
