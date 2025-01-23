package ru.netology.moneytransferservice.service;

import jakarta.annotation.PostConstruct;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.netology.moneytransferservice.exception.TransferException;
import ru.netology.moneytransferservice.model.Amount;
import ru.netology.moneytransferservice.model.Commission;
import ru.netology.moneytransferservice.model.TransferRequest;
import ru.netology.moneytransferservice.model.TransferResponse;
import ru.netology.moneytransferservice.repository.CardRepository;
import ru.netology.moneytransferservice.repository.TransferRepository;
import ru.netology.moneytransferservice.util.FileLogger;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class TransferService {

    private Commission commission;
    private final TransferRepository transferRepository;
    private final CardRepository cardRepository;
    private final FileLogger fileLogger;
    private ExecutorService executorService;

    public TransferService(TransferRepository transferRepository,
                           FileLogger fileLogger,
                           CardRepository cardRepository,
                           Commission commission) {
        this.transferRepository = transferRepository;
        this.cardRepository = cardRepository;
        this.fileLogger = fileLogger;
    }

    @PostConstruct
    public void init() {
        executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    }

    public TransferResponse execute(@NotNull TransferRequest request)
            throws TransferException {
        CompletableFuture<TransferResponse> thread = CompletableFuture.supplyAsync(() -> {
            String operationId = UUID.randomUUID().toString();
            // Логика перевода средств

            

            String result = "PROGRESS";

            fileLogger.logTransfer(
                    request.getCardFrom(),
                    request.getCardTo(),
                    request.getAmount().getAmount(),
                    commission.getCommission(),
                    result
            );

            transferRepository.save(request, result, operationId);

            return new TransferResponse(operationId);
        }, executorService);

        try {
            return thread.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new TransferException(500,
                    "Something wrong in the transfer thread pool: "
                    + e.getMessage());
        }
    }
}
