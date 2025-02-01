package org.loan.loanservice.exception;

import lombok.Getter;
import org.loan.loanservice.config.Constants;

@Getter
public class AmountException extends ApiException {
    private final int code;

    public AmountException() {
        super(Constants.ErrorMessage.INVALID_AMOUNT);
        this.code = Constants.ErrorCode.INVALID_AMOUNT;
    }
}
