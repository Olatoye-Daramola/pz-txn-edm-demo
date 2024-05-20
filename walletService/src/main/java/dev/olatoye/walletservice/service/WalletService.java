package dev.olatoye.walletservice.service;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.olatoye.walletservice.model.entity.Wallet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WalletService {

    private final DynamoDBMapper dynamoDBMapper;
    private final ObjectMapper objectMapper;
    private final Logger LOG = LoggerFactory.getLogger(WalletService.class);

    public WalletService(DynamoDBMapper dynamoDBMapper, ObjectMapper objectMapper) {
        this.dynamoDBMapper = dynamoDBMapper;
        this.objectMapper = objectMapper;
    }

    public Wallet findByOwnerUniqueId(String uniqueId) {
        Map<String, AttributeValue> exp = new HashMap<>();
        exp.put(":uniqueId", new AttributeValue().withS(uniqueId));

        DynamoDBQueryExpression<Wallet> queryExpression = new DynamoDBQueryExpression<Wallet>()
                .withIndexName("ownerUniqueId-index")
                .withConsistentRead(false)
                .withKeyConditionExpression("ownerUniqueId = :uniqueId")
                .withExpressionAttributeValues(exp)
                .withLimit(1);
        List<Wallet> resultSet = dynamoDBMapper.query(Wallet.class, queryExpression);
        return resultSet.isEmpty() ? null : resultSet.get(0);
    }

    public void save(Wallet wallet) {
        dynamoDBMapper.save(wallet);
    }
}
