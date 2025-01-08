package ru.netology.moneytransferservice.model;

import lombok.Data;

@Data
public class TransferResponse {
    private String result;

    public TransferResponse(String result) {
        this.result = result;
    }
}