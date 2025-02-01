package org.loan.loanservice.exception;

import lombok.Getter;
import org.loan.loanservice.config.Constants;

@Getter
public class InvalidInstallmentPeriodException extends ApiException {
    private final int code;

    public InvalidInstallmentPeriodException() {
        super(Constants.ErrorMessage.INVALID_INSTALLMENT_PERIOD);
        this.code = Constants.ErrorCode.INVALID_INSTALLMENT_PERIOD;
    }
}
