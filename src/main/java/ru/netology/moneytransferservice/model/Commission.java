package ru.netology.moneytransferservice.model;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class Commission {

    @Value("${application.fee}")
    private double fee;
    private double commission;

    public Commission(Amount amount) {
        commission = amount.getAmount() * fee;
    }
}
