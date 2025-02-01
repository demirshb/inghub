package org.loan.loanservice.config;

public class Constants {
    public static class ErrorCode {
        public static final int CUSTOMER_DOES_NOT_EXIST = 1000;
        public static final int INVALID_ROLE = 2000;
        public static final int INTERNAL_ERROR = 5000;
        public static final int CUSTOMER_EXIST = 3000;
        public static final int VALIDATION_ERROR = 10000;
        public static final int INVALID_INSTALLMENT_PERIOD = 7000;
        public static final int CREDIT_INSUFFICIENT = 7100;
        public static final int INVALID_AMOUNT = 7200;
        public static final int LOAN_EXIST = 7300;
        public static final int LOAN_DOES_NOT_EXIST = 7400;
        public static final int LOAN_IS_CLOSED = 7500;
    }

    public static class ErrorMessage {
        public static final String CUSTOMER_DOES_NOT_EXIST = "Customer does not exist";
        public static final String INVALID_ROLE = "Invalid role";
        public static final String CUSTOMER_EXIST = "Customer already exist";
        public static final String VALIDATION_ERROR = "Validation error";
        public static final String INVALID_INSTALLMENT_PERIOD = "Period can only be 6-9-12-24";
        public static final String CREDIT_INSUFFICIENT = "Credit insufficient";
        public static final String INVALID_AMOUNT = "Invalid amount";
        public static final String LOAN_EXIST = "Customer has open loan";
        public static final String LOAN_DOES_NOT_EXIST = "loan does not exist";
        public static final String LOAN_IS_CLOSED = "Loan is closed";
    }
}
