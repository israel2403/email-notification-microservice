package com.huerta.email_notification_microservice.error;

public class NotRetryableException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public NotRetryableException(String message) {
        super(message);
    }

    public NotRetryableException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotRetryableException(Throwable cause) {
        super(cause);
    }

}
