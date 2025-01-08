package ru.netology.moneytransferservice.exception;

import lombok.Getter;

@Getter
public class TransferException extends RuntimeException {

    private final int errorCode;

    public TransferException(int errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public TransferException(int errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }
}
