package dev.olatoye.walletservice.service;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import dev.olatoye.walletservice.model.dto.request.InitiateTransactionRequest;
import dev.olatoye.walletservice.model.entity.Transaction;
import dev.olatoye.walletservice.model.entity.Wallet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.cloud.aws.messaging.listener.SqsMessageDeletionPolicy;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class TransactionService {

    @Value("${cloud.aws.notification.queue}")
    private String notificationQueue;

    private final WalletService walletService;
    private final DynamoDBMapper dynamoDBMapper;
    private final QueueMessagingTemplate queueMessagingTemplate;
    private final ObjectMapper objectMapper;
    private final Logger LOG = LoggerFactory.getLogger(TransactionService.class);
    Set<String> processedMessages = ConcurrentHashMap.newKeySet();

    public TransactionService(WalletService walletService, DynamoDBMapper dynamoDBMapper, QueueMessagingTemplate queueMessagingTemplate, ObjectMapper objectMapper) {
        this.walletService = walletService;
        this.dynamoDBMapper = dynamoDBMapper;
        this.queueMessagingTemplate = queueMessagingTemplate;
        this.objectMapper = objectMapper;
    }

    @SqsListener(value = "transaction-sqs-queue", deletionPolicy = SqsMessageDeletionPolicy.NEVER)
    public void logTransaction(String message, @Header(name = "MessageId") String messageId) {

        boolean isAdded = processedMessages.add(messageId);
        if (!isAdded) return;

        InitiateTransactionRequest txnRequest = new Gson().fromJson(message, InitiateTransactionRequest.class);
        boolean registeredTxn = save(txnRequest);
        //Pull customer email from db
        //sendNotificationToQueue(customer.getEmail());
    }

    private boolean save(InitiateTransactionRequest txnRequest) {
        try {
            //Handle Transaction creation
            Transaction txn = createTxnObject(txnRequest);
            //Validate transaction with wallet position
            Wallet wallet = walletService.findByOwnerUniqueId(txn.getDestinationUniqueId());
            if (!isValidTransaction(txn, wallet)) return false;
            //Save transaction instruments
            txn.setSuccessful(true);
            dynamoDBMapper.save(txn);
            walletService.save(wallet);
            return true;
        } catch (Exception e) {
            // Rollback saved details
            return false;
        }
    }

    private Transaction createTxnObject(InitiateTransactionRequest txnRequest) {
        Transaction txn = objectMapper.convertValue(txnRequest, Transaction.class);
        txn.setVat(0.5);
        BigDecimal vatAmount = txn.getAmount().multiply(BigDecimal.valueOf(txn.getVat())).setScale(2, RoundingMode.HALF_EVEN);
        BigDecimal settledAmount = txn.isCreditTransaction()
                ? txn.getAmount().subtract(vatAmount) : txn.getAmount().add(vatAmount);
        txn.setSettledAmount(settledAmount.setScale(2, RoundingMode.HALF_EVEN));
        return txn;
    }

    private boolean isValidTransaction(Transaction txn, Wallet wallet) {
        if (!txn.isCreditTransaction()) {
            int isTxnAmountMoreThanAvailableBalance = wallet.getBalance().compareTo(txn.getSettledAmount());
            if (isTxnAmountMoreThanAvailableBalance < 0) return false;
            wallet.setBalance(wallet.getBalance().subtract(txn.getSettledAmount()));
        }
        else {
            int isTxnAmountMoreThanAllowedCreditLimit = wallet.getAmountLimit().compareTo(txn.getSettledAmount());
            if (isTxnAmountMoreThanAllowedCreditLimit < 0) return false;
            wallet.setBalance(wallet.getBalance().add(txn.getSettledAmount()));
        }
        return true;
    }

    private void sendNotificationToQueue(String email) {
        Message<String> message = MessageBuilder.withPayload("Your account has been created. You can proceed with transacting" +
                " with your account.").build();
        queueMessagingTemplate.send(email, message);
    }
}
