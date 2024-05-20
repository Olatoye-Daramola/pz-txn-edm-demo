package dev.olatoye.transactionservice.service;

import dev.olatoye.transactionservice.model.dto.request.InitiateTransactionRequest;
import dev.olatoye.transactionservice.model.dto.response.InitiateTransactionResponse;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TransactionService {

    private final SNSService snsService;

    public TransactionService(SNSService snsService) {
        this.snsService = snsService;
    }

    public InitiateTransactionResponse initiateTransaction(InitiateTransactionRequest request) {
        String initiationTransactionReference = UUID.randomUUID().toString();
        InitiateTransactionResponse response = new InitiateTransactionResponse();
        response.setInitiationTransactionReference(initiationTransactionReference);
        request.setInitiationTransactionReference(initiationTransactionReference);
        snsService.publishCustomerDetails(request);
        return response;
    }
}
