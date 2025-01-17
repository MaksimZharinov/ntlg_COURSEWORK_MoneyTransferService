package ru.netology.moneytransferservice.model;

import lombok.Data;

@Data
public class TransferResponse {
    private String operationId;

    public TransferResponse(String operationId) {
        this.operationId = operationId;
    }
}