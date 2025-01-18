package ru.netology.moneytransferservice.service;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.netology.moneytransferservice.exception.TransferException;
import ru.netology.moneytransferservice.model.Amount;
import ru.netology.moneytransferservice.model.TransferRequest;
import ru.netology.moneytransferservice.model.TransferResponse;
import ru.netology.moneytransferservice.repository.TransferRepository;
import ru.netology.moneytransferservice.util.FileLogger;

import java.util.UUID;

@Service
public class TransferService {

    @Value("${application.fee}")
    private double fee;
    private final TransferRepository transferRepository;
    private final FileLogger fileLogger;

    public TransferService(TransferRepository transferRepository, FileLogger fileLogger) {
        this.transferRepository = transferRepository;
        this.fileLogger = fileLogger;
    }

    public TransferResponse execute(@NotNull TransferRequest request)
            throws TransferException {
        var operationId = UUID.randomUUID().toString();
        // Логика перевода средств
        String result = "SUCCESS";
        double commission = calculateCommission(request.getAmount());

        fileLogger.logTransfer(
                request.getCardFrom(),
                request.getCardTo(),
                request.getAmount().getAmount(),
                commission,
                result
        );

        transferRepository.save(request, result, operationId);

        return new TransferResponse(operationId);
    }

    private double calculateCommission(Amount amount) {
        return amount.getAmount() * fee;
    }
}
