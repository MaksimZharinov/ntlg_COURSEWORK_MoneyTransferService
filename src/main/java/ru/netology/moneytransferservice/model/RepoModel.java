package ru.netology.moneytransferservice.model;

import org.springframework.stereotype.Component;
import ru.netology.moneytransferservice.exception.RepoException;

import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RepoModel {
    private static final ConcurrentHashMap<String, TransferRecord> repo = new ConcurrentHashMap<>();

    public void addData(String operationId, TransferRecord record) {
        if (repo.containsKey(operationId)) {
            throw new RepoException(500, "Failed to save data in the repository. Use UPDATE() method.");
        }
        repo.put(operationId, record);
    }

    public void updateData(TransferRecord record) {
        repo.put(record.getOperationId(), record);
    }

    public Collection<TransferRecord> getData() {
        return repo.values();
    }

    public Optional<TransferRecord> getRecordById(String operationId) {
        return Optional.ofNullable(repo.get(operationId));
    }

    public boolean isEmpty() {
        return repo.isEmpty();
    }
}
