package ru.netology.moneytransferservice.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.netology.moneytransferservice.exception.TransferException;
import ru.netology.moneytransferservice.model.TransferRequest;
import ru.netology.moneytransferservice.model.TransferResponse;
import ru.netology.moneytransferservice.service.TransferService;

@RestController
public class TransferController {

    private final TransferService transferService;

    public TransferController(TransferService transferService) {
        this.transferService = transferService;
    }

    @PostMapping("/transfer")
    public TransferResponse transfer(@RequestBody TransferRequest request)
            throws TransferException {
        return transferService.execute(request);
    }
}
