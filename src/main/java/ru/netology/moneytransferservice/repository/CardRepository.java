package ru.netology.moneytransferservice.repository;

import org.springframework.stereotype.Repository;
import ru.netology.moneytransferservice.model.CardModel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class CardRepository {

    private final ConcurrentHashMap<String, CardModel> repo = new ConcurrentHashMap<>();

    public CardModel getByCardNumber(String cardNumber) {
        return repo.get(cardNumber);
    }

    public List<CardModel> getAll() {
        return new ArrayList<>(repo.values());
    }

    public void addCard(CardModel card) {
        repo.put(card.getCardNumber(), card);
    }
}
