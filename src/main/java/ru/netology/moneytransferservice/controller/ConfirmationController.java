package ru.netology.moneytransferservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.netology.moneytransferservice.exception.TransferException;
import ru.netology.moneytransferservice.model.ConfirmationRequest;
import ru.netology.moneytransferservice.service.TransferService;

import java.io.IOException;

@RestController
public class ConfirmationController {

    private final TransferService transferService;

    public ConfirmationController(TransferService transferService) {
        this.transferService = transferService;
    }

    @PostMapping("/confirmOperation")
    public ResponseEntity<String> confirmOperation(@RequestBody ConfirmationRequest request)
            throws IOException {
        try {
            boolean confirmed = transferService.confirmOperation(request);
            if (confirmed) {
                return new ResponseEntity<>("Operation confirmed successfully", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Failed to confirm operation", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (TransferException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.valueOf(e.getErrorCode()));
        }
    }
}
