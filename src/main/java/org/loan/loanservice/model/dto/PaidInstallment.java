package org.loan.loanservice.model.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class PaidInstallment {
    private Integer id;
    private BigDecimal paidAmount;
    private LocalDate dueDate;
}
