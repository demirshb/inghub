package org.loan.loanservice.exception.handler;

import lombok.extern.slf4j.Slf4j;
import org.loan.loanservice.config.Constants;
import org.loan.loanservice.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDate;
import java.util.List;

@RestControllerAdvice
@Slf4j
public class CustomExceptionHandler {

    @ExceptionHandler({Exception.class})
    public ResponseEntity<Error> handleException(Exception e) {
        log.error("An unknown exception occurred!", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(generateError(Constants.ErrorCode.INTERNAL_ERROR, e.getMessage()));
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<Error> handleException(MethodArgumentNotValidException e) {
        List<ObjectError> allErrors = e.getBindingResult().getAllErrors();
        if (!allErrors.isEmpty()) {
            String defaultMessage = allErrors.get(0).getDefaultMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(generateError(Constants.ErrorCode.VALIDATION_ERROR, defaultMessage));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(generateError(Constants.ErrorCode.VALIDATION_ERROR, Constants.ErrorMessage.VALIDATION_ERROR));
    }


    @ExceptionHandler({UserExistException.class})
    public ResponseEntity<Error> handleException(UserExistException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(generateError(e.getCode(), e.getMessage()));
    }

    @ExceptionHandler({UserDoesNotExistException.class})
    public ResponseEntity<Error> handleException(UserDoesNotExistException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(generateError(e.getCode(), e.getMessage()));
    }

    @ExceptionHandler({InvalidRoleException.class})
    public ResponseEntity<Error> handleException(InvalidRoleException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(generateError(e.getCode(), e.getMessage()));
    }

    @ExceptionHandler({InvalidInstallmentPeriodException.class})
    public ResponseEntity<Error> handleException(InvalidInstallmentPeriodException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(generateError(e.getCode(), e.getMessage()));
    }

    @ExceptionHandler({LoanAlreadyExistException.class})
    public ResponseEntity<Error> handleException(LoanAlreadyExistException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(generateError(e.getCode(), e.getMessage()));
    }

    @ExceptionHandler({CreditInsufficentException.class})
    public ResponseEntity<Error> handleException(CreditInsufficentException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(generateError(e.getCode(), e.getMessage()));
    }

    @ExceptionHandler({LoanIsClosedException.class})
    public ResponseEntity<Error> handleException(LoanIsClosedException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(generateError(e.getCode(), e.getMessage()));
    }

    @ExceptionHandler({LoanDoesNotExistException.class})
    public ResponseEntity<Error> handleException(LoanDoesNotExistException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(generateError(e.getCode(), e.getMessage()));
    }

    @ExceptionHandler({AmountException.class})
    public ResponseEntity<Error> handleException(AmountException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(generateError(e.getCode(), e.getMessage()));
    }


    private Error generateError(int code, String message) {
        return Error.builder()
                .code(code)
                .message(message)
                .timestamp(LocalDate.now())
                .build();
    }
}
