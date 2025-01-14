package ru.netology.moneytransferservice.service;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.netology.moneytransferservice.exception.TransferException;
import ru.netology.moneytransferservice.model.ConfirmationRequest;
import ru.netology.moneytransferservice.model.TransferRecord;
import ru.netology.moneytransferservice.model.TransferRequest;
import ru.netology.moneytransferservice.model.TransferResponse;
import ru.netology.moneytransferservice.repository.TransferRepository;
import ru.netology.moneytransferservice.util.FileLogger;

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
        // Логика перевода средств
        String result = "SUCCESS";
        double commission = calculateCommission(request.getAmount());

        fileLogger.logTransfer(
                request.getCardFrom(),
                request.getCardTo(),
                request.getAmount(),
                commission,
                result
        );

        transferRepository.save(request, result);

        return new TransferResponse(result);
    }

    public boolean confirmOperation(@NotNull ConfirmationRequest request)
            throws TransferException {
        // Получаем запись о переводе по operationId
        TransferRecord record = transferRepository.findByOperationId(request.getOperationId())
                .orElseThrow(() -> new TransferException(404, "Operation not found"));

        // Проверяем код подтверждения
        if (!request.getCode().equals(record.getVerificationCode())) {
            throw new TransferException(401, "Invalid verification code");
        }

        // Подтверждаем операцию
        record.setConfirmed(true);
        transferRepository.update(record);

        return true;
    }


    private double calculateCommission(double amount) {
        return amount * fee;
    }
}
