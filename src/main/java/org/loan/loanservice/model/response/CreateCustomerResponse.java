package org.loan.loanservice.model.response;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CreateCustomerResponse {
    private Integer customerId;
}