package dev.olatoye.onboardingservice.service;

import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.PublishRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SNSService {

    @Value("${onboarding-topic-arn}")
    private String ONBOARDING_TOPIC_ARN;

    private final AmazonSNSClient amazonSNSClient;

    public SNSService(AmazonSNSClient amazonSNSClient) {
        this.amazonSNSClient = amazonSNSClient;
    }

    public boolean publishCustomerDetails(Object object) {
        PublishRequest publishRequest = new PublishRequest(ONBOARDING_TOPIC_ARN, object.toString());
        amazonSNSClient.publish(publishRequest);
        return true;
    }
}
