package ru.netology.moneytransferservice.model;

import lombok.Data;

@Data
public class Amount {
    private Integer amount;
    private String currency;
}
