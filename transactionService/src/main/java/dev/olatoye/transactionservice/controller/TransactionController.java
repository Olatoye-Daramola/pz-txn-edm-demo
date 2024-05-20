package dev.olatoye.transactionservice.controller;

import dev.olatoye.transactionservice.model.dto.request.InitiateTransactionRequest;
import dev.olatoye.transactionservice.model.dto.response.InitiateTransactionResponse;
import dev.olatoye.transactionservice.service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.HashMap;
import java.util.Map;

@RestController
@EnableWebMvc
@RequestMapping("/transaction")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping
    public ResponseEntity<Map<String, String>> ping() {
        Map<String, String> pong = new HashMap<>();
        pong.put("pong", "Hello, World!");
        return ResponseEntity.ok().body(pong);
    }

    @PostMapping
    public ResponseEntity<InitiateTransactionResponse> initiateTransaction(@RequestBody InitiateTransactionRequest request) {
        return ResponseEntity.ok().body(transactionService.initiateTransaction(request));
    }
}
