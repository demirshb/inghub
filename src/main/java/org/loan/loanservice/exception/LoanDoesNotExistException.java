package org.loan.loanservice.exception;

import lombok.Getter;
import org.loan.loanservice.config.Constants;

@Getter
public class LoanDoesNotExistException extends ApiException {
    private final int code;

    public LoanDoesNotExistException() {
        super(Constants.ErrorMessage.LOAN_DOES_NOT_EXIST);
        this.code = Constants.ErrorCode.LOAN_DOES_NOT_EXIST;
    }
}
