package ru.netology.moneytransferservice.exception;

import lombok.Getter;

@Getter
public class ConfirmationException extends RuntimeException {
    private final int errorCode;

    public ConfirmationException(int errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public ConfirmationException(int errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }
}
