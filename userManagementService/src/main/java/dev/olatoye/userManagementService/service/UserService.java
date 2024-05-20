package dev.olatoye.userManagementService.service;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import dev.olatoye.userManagementService.model.dto.request.CreateUserDto;
import dev.olatoye.userManagementService.model.entity.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.aws.messaging.listener.SqsMessageDeletionPolicy;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class UserService {

    private final DynamoDBMapper dynamoDBMapper;
    private final ObjectMapper objectMapper;
    private final Logger LOG = LoggerFactory.getLogger(UserService.class);
    Set<String> processedMessages = ConcurrentHashMap.newKeySet();

    public UserService(DynamoDBMapper dynamoDBMapper, ObjectMapper objectMapper) {
        this.dynamoDBMapper = dynamoDBMapper;
        this.objectMapper = objectMapper;
    }

    @SqsListener(value = "customer-sqs-queue", deletionPolicy = SqsMessageDeletionPolicy.NEVER)
    public void createUser(String message, @Header(name = "MessageId") String messageId) {

        boolean isAdded = processedMessages.add(messageId);
        if (!isAdded) return;
        CreateUserDto dto = new Gson().fromJson(message, CreateUserDto.class);
        boolean savedCustomer = save(dto);
    }

    private boolean save(CreateUserDto customerToSave) {
        try {
            Customer customer = objectMapper.convertValue(customerToSave, Customer.class);
            customer.setTimeCreated(new Date());
            dynamoDBMapper.save(customer);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
