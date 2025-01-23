package ru.netology.moneytransferservice.service;

import jakarta.annotation.PostConstruct;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import ru.netology.moneytransferservice.exception.ConfirmationException;
import ru.netology.moneytransferservice.exception.TransferException;
import ru.netology.moneytransferservice.model.*;
import ru.netology.moneytransferservice.repository.CardRepository;
import ru.netology.moneytransferservice.repository.TransferRepository;
import ru.netology.moneytransferservice.util.FileLogger;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class ConfirmationService {

    private final TransferRepository transferRepository;
    private final FileLogger fileLogger;
    private final CardRepository cardRepository;
    private ExecutorService executorService;
    private Commission commission;

    public ConfirmationService(TransferRepository transferRepository,
                               CardRepository cardRepository,
                               FileLogger fileLogger,
                               Commission commission) {
        this.transferRepository = transferRepository;
        this.cardRepository = cardRepository;
        this.fileLogger = fileLogger;
        this.commission = commission;
    }

    @PostConstruct
    public void init() {
        executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    }

    public ConfirmationResponse execute(@NotNull ConfirmationRequest request)
            throws ConfirmationException {
        CompletableFuture<ConfirmationResponse> thread = CompletableFuture.supplyAsync(() -> {
            TransferRecord record = transferRepository.findByOperationId(request.getOperationId())
                    .orElseThrow(() -> new ConfirmationException(400, "Operation not found"));
            if (!request.getCode().equals(record.getVerificationCode())) {
                record.setResult("ERROR");
                transferRepository.update(record);
                fileLogger.logTransfer(
                        record.getRequest().getCardFrom(),
                        record.getRequest().getCardTo(),
                        record.getRequest().getAmount().getAmount(),
                        commission.getCommission(),
                        record.getResult()
                );
                throw new ConfirmationException(400, "Invalid verification code");
            }

            record.setConfirmed(true);
            record.setResult("SUCCESS");
            transferRepository.update(record);
            fileLogger.logTransfer(
                    record.getRequest().getCardFrom(),
                    record.getRequest().getCardTo(),
                    record.getRequest().getAmount().getAmount(),
                    commission.getCommission(),
                    record.getResult()
            );
            return new ConfirmationResponse(record.getOperationId());
        }, executorService);

        try {
            return thread.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new TransferException(500,
                    "Something wrong in the confirmation thread pool: "
                    + e.getMessage());
        }
    }
}
