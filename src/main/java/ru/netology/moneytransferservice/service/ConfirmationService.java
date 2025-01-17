package ru.netology.moneytransferservice.service;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import ru.netology.moneytransferservice.exception.ConfirmationException;
import ru.netology.moneytransferservice.model.ConfirmationRequest;
import ru.netology.moneytransferservice.model.ConfirmationResponse;
import ru.netology.moneytransferservice.model.TransferRecord;
import ru.netology.moneytransferservice.repository.Repository;

@Service
public class ConfirmationService {

    private final Repository repository;

    public ConfirmationService(Repository repository) {
        this.repository = repository;
    }

    public ConfirmationResponse execute(@NotNull ConfirmationRequest request)
            throws ConfirmationException {

        TransferRecord record = repository.findByOperationId(request.getOperationId())
                .orElseThrow(() -> new ConfirmationException(400, "Operation not found"));
        if (!request.getCode().equals(record.getVerificationCode())) {
            throw new ConfirmationException(400, "Invalid verification code");
        }

        record.setConfirmed(true);
        repository.update(record);

        return new ConfirmationResponse(record.getOperationId());
    }
}
