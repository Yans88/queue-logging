package com.yansen.exceptions;

public class ValidationUsernameEmailException extends RuntimeException {
    public ValidationUsernameEmailException() {
        super();
    }

    public ValidationUsernameEmailException(String message, Throwable cause) {
        super(message, cause);
    }

    public ValidationUsernameEmailException(String message) {
        super(message);
    }

    public ValidationUsernameEmailException(Throwable cause) {
        super(cause);
    }
}
