package ru.netology.moneytransferservice.exception;

public class RepoException extends RuntimeException {

    private final int errorCode;

    public RepoException(int errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public RepoException(int errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }
}