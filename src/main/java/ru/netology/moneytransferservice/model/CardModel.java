package ru.netology.moneytransferservice.model;

import lombok.Data;

@Data
public class CardModel {
    private String cardNumber;
    private String cardValidTill;
    private String cardCVV;
    private Long balance;
}
