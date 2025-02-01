package org.loan.loanservice.exception;

import lombok.Getter;
import org.loan.loanservice.config.Constants;

@Getter
public class LoanIsClosedException extends ApiException {
    private final int code;

    public LoanIsClosedException() {
        super(Constants.ErrorMessage.LOAN_IS_CLOSED);
        this.code = Constants.ErrorCode.LOAN_IS_CLOSED;
    }
}
