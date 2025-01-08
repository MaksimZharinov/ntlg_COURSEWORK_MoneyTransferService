package ru.netology.moneytransferservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class TransferRecord {
    private String operationId;
    private LocalDateTime timestamp;
    private TransferRequest request;
    private String result;
    private boolean confirmed;
    private String verificationCode;

    public TransferRecord(String operationId,
                          LocalDateTime timestamp,
                          TransferRequest request,
                          String result) {
        this.operationId = operationId;
        this.timestamp = timestamp;
        this.request = request;
        this.result = result;
        this.confirmed = false;
        this.verificationCode = "";
    }
}
