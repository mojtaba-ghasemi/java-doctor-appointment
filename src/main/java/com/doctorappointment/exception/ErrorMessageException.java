package com.doctorappointment.exception;


public class ErrorMessageException extends Exception {

    public ErrorMessageException(ErrorMessage errorMessage) {
        this.errorMessage = errorMessage;
    }

    public ErrorMessageException(int status, int code, String errorMessage) {
        super(errorMessage);
        this.errorMessage = new ErrorMessage(status, code, errorMessage);
    }

    ErrorMessage errorMessage;

    public ErrorMessage getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(ErrorMessage errorMessage) {
        this.errorMessage = errorMessage;
    }
}
