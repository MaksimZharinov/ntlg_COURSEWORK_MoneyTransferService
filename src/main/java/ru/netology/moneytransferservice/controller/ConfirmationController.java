package ru.netology.moneytransferservice.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.netology.moneytransferservice.exception.ConfirmationException;
import ru.netology.moneytransferservice.model.ConfirmationRequest;
import ru.netology.moneytransferservice.model.ConfirmationResponse;
import ru.netology.moneytransferservice.service.ConfirmationService;

@RestController
public class ConfirmationController {

    private final ConfirmationService confirmationService;

    public ConfirmationController(ConfirmationService confirmationService) {
        this.confirmationService = confirmationService;
    }

    @PostMapping("/confirmOperation")
    public ConfirmationResponse confirmOperation(@RequestBody ConfirmationRequest request)
            throws ConfirmationException {
        return confirmationService.execute(request);
    }
}
