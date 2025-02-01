package org.loan.loanservice.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.loan.loanservice.exception.InvalidInstallmentPeriodException;
import org.loan.loanservice.exception.InvalidRoleException;

import java.util.Objects;

@Getter
@AllArgsConstructor
public enum NumberOfInstallment {
    I6(6),
    I9(9),
    I12(12),
    I24(24);

    private final int numberOfInstallment;

    public static int fromValue(int installment) {
        for (NumberOfInstallment r : values()) {
            if (installment == r.getNumberOfInstallment()) {
                return r.numberOfInstallment;
            }
        }
        throw new InvalidInstallmentPeriodException();
    }
}
