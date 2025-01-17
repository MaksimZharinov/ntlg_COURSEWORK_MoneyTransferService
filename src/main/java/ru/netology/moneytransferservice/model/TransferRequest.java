package ru.netology.moneytransferservice.model;

import lombok.Data;

@Data
public class TransferRequest {
    private String cardFrom;
    private String cardFromValidTill;
    private String cardFromCVV;
    private String cardTo;
    private Amount amount;
}