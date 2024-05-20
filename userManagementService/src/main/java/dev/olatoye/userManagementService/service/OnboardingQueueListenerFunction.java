package dev.olatoye.userManagementService.service;

import com.amazonaws.services.sqs.AmazonSQSClient;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import dev.olatoye.userManagementService.model.dto.request.AwsSQSRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.function.Function;

@Configuration
public class OnboardingQueueListenerFunction implements Function<AwsSQSRequest, String> {

    @Value("${cloud.aws.onboarding.queue-url}")
    private String onboardingQueueUrl;

    private final AmazonSQSClient amazonSQS;
    private final UserService userService;

    public OnboardingQueueListenerFunction(AmazonSQSClient amazonSQS, UserService userService) {
        this.amazonSQS = amazonSQS;
        this.userService = userService;
    }

    @Override
    public String apply(AwsSQSRequest unused) {
        ReceiveMessageRequest receiveMessageRequest = new ReceiveMessageRequest(onboardingQueueUrl)
                .withWaitTimeSeconds(5)
                .withMaxNumberOfMessages(10);
        List<Message> messages = amazonSQS.receiveMessage(receiveMessageRequest).getMessages();
        for (Message message : messages) {
            userService.createUser(message.getBody(), message.getMessageId());
        }
        return "SUCCESSFUL";
    }
}
