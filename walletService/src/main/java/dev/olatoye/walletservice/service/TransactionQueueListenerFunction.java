package dev.olatoye.walletservice.service;

import com.amazonaws.services.sqs.AmazonSQSAsyncClient;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import dev.olatoye.walletservice.model.dto.request.AwsSQSRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.function.Function;

@Configuration
public class TransactionQueueListenerFunction implements Function<AwsSQSRequest, String> {

    @Value("${cloud.aws.transaction.queue-url}")
    private String transactionQueueUrl;

    private final AmazonSQSAsyncClient amazonSQSClient;
    private final TransactionService transactionService;

    public TransactionQueueListenerFunction(AmazonSQSAsyncClient amazonSQSClient, TransactionService transactionService) {
        this.amazonSQSClient = amazonSQSClient;
        this.transactionService = transactionService;
    }

    @Override
    public String apply(AwsSQSRequest awsSQSRequest) {
        ReceiveMessageRequest receiveMessageRequest = new ReceiveMessageRequest(transactionQueueUrl)
                .withWaitTimeSeconds(5)
                .withMaxNumberOfMessages(10);
        List<Message> messages = amazonSQSClient.receiveMessage(receiveMessageRequest).getMessages();
        for (Message message : messages) {
            transactionService.logTransaction(message.getBody(), message.getMessageId());
        }
        return "SUCCESSFUL";
    }
}
