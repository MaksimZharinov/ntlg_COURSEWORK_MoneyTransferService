package ru.netology.moneytransferservice.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import ru.netology.moneytransferservice.model.TransferRecord;
import ru.netology.moneytransferservice.model.TransferRequest;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class TransferRepository {

    private static final ObjectMapper mapper = new ObjectMapper(new YAMLFactory());

    @Value("${repository}")
    private String dataFilePath;

    public void save(TransferRequest request, String result) throws IOException {
        List<TransferRecord> records = loadRecords();
        String operationId = UUID.randomUUID().toString();
        records.add(new TransferRecord(operationId, LocalDateTime.now(), request, result));
        Files.writeString(Paths.get(dataFilePath), mapper.writeValueAsString(records));
    }

    public Optional<TransferRecord> findByOperationId(String operationId) throws IOException {
        List<TransferRecord> records = loadRecords();
        return records.stream()
                .filter(r -> r.getOperationId().equals(operationId))
                .findFirst();
    }

    public void update(TransferRecord record) throws IOException {
        List<TransferRecord> records = loadRecords();
        records.removeIf(r -> r.getOperationId().equals(record.getOperationId()));
        records.add(record);
        Files.writeString(Paths.get(dataFilePath), mapper.writeValueAsString(records));
    }

    public List<TransferRecord> findAll() throws IOException {
        return loadRecords();
    }

    private List<TransferRecord> loadRecords() throws IOException {
        if (!Files.exists(Paths.get(dataFilePath))) {
            Files.createFile(Paths.get(dataFilePath));
            return new ArrayList<>();
        }
        return mapper.readValue(new File(dataFilePath),
                mapper.getTypeFactory()
                        .constructCollectionType(List.class, TransferRecord.class));
    }
}
