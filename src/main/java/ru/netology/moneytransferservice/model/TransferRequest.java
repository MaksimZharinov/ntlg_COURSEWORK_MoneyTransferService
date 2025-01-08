package ru.netology.moneytransferservice.model;

import lombok.Data;

@Data
public class TransferRequest {
    private String cardFrom;
    private String cardTo;
    private double amount;
}