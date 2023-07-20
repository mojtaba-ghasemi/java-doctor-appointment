package com.doctorappointment.exception;


public class ErrorMessage {

    int code;
    int status;
    String message;

    public ErrorMessage(int status, int code, String message) {
        this.status = status;
        this.message = message;
        this.code = code;
    }

    public int getCode()
    {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}
