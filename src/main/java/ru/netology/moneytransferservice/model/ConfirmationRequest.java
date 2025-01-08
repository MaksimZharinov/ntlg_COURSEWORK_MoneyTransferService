package ru.netology.moneytransferservice.model;

import lombok.Data;

@Data
public class ConfirmationRequest {
    private String operationId;
    private String code;
}
