package org.loan.loanservice.exception.handler;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Builder
@AllArgsConstructor
@Getter
@Setter
public class Error {
    private int code;
    private String message;
    private LocalDate timestamp;
}
