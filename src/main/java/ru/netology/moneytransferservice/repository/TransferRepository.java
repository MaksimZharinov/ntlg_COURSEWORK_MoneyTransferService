package ru.netology.moneytransferservice.repository;

import org.springframework.stereotype.Repository;
import ru.netology.moneytransferservice.model.RepoModel;
import ru.netology.moneytransferservice.model.TransferRecord;
import ru.netology.moneytransferservice.model.TransferRequest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class TransferRepository {

    private RepoModel repo;

    public void save(TransferRequest request, String result, String operationId) {
        TransferRecord record = new TransferRecord(operationId, LocalDateTime.now(), request, result);
        repo.addData(operationId, record);
    }

    public Optional<TransferRecord> findByOperationId(String operationId) {
        return repo.getRecordById(operationId);
    }

    public void update(TransferRecord record) {
        repo.updateData(record);
    }

    public List<TransferRecord> findAll() {
        return loadRecords();
    }

    private List<TransferRecord> loadRecords() {
        if (repo.isEmpty()) {
            return new ArrayList<>();
        }
        return new ArrayList<>(repo.getData());
    }
}
