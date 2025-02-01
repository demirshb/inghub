package org.loan.loanservice.exception;

import lombok.Getter;
import org.loan.loanservice.config.Constants;

@Getter
public class UserExistException extends ApiException {
    private final int code;

    public UserExistException() {
        super(Constants.ErrorMessage.CUSTOMER_EXIST);
        this.code = Constants.ErrorCode.CUSTOMER_EXIST;
    }
}
