package org.loan.loanservice.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PaymentStatus {
    PAID(0),
    WAITING(1);

    private final int paymentStatus;

    public static int fromValue(int paymentStatus) {
        for (PaymentStatus r : values()) {
            if (paymentStatus == r.getPaymentStatus()) {
                return r.getPaymentStatus();
            }
        }
        return WAITING.getPaymentStatus();
    }
}
