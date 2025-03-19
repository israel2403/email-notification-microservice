package com.huerta.email_notification_microservice.error;

public class RetryableException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public RetryableException(String message) {
        super(message);
    }

    public RetryableException(String message, Throwable cause) {
        super(message, cause);
    }

    public RetryableException(Throwable cause) {
        super(cause);
    }

}
