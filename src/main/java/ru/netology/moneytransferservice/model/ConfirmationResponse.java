package ru.netology.moneytransferservice.model;

import lombok.Data;

@Data
public class ConfirmationResponse {
    private String operationId;

    public ConfirmationResponse(String operationId) {
        this.operationId = operationId;
    }
}
