package org.loan.loanservice.exception;

import lombok.Getter;
import org.loan.loanservice.config.Constants;

@Getter
public class CreditInsufficentException extends ApiException {
    private final int code;

    public CreditInsufficentException() {
        super(Constants.ErrorMessage.CREDIT_INSUFFICIENT);
        this.code = Constants.ErrorCode.CREDIT_INSUFFICIENT;
    }
}
