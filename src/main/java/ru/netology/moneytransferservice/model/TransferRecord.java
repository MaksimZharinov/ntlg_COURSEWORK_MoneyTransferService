package ru.netology.moneytransferservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class TransferRecord {

    @Value("${application.verification.code}")
    private String verificationCode;
    private String operationId;
    private LocalDateTime timestamp;
    private TransferRequest request;
    private String result;
    private boolean confirmed;

    public TransferRecord(String operationId,
                          LocalDateTime timestamp,
                          TransferRequest request,
                          String result) {
        this.operationId = operationId;
        this.timestamp = timestamp;
        this.request = request;
        this.result = result;
        this.confirmed = false;
    }
}
