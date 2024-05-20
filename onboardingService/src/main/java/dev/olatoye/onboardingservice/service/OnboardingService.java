package dev.olatoye.onboardingservice.service;

import dev.olatoye.onboardingservice.model.dto.request.CreateCustomerRequest;
import dev.olatoye.onboardingservice.model.dto.response.CreateCustomerResponse;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class OnboardingService {

    private final SNSService snsService;

    public OnboardingService(SNSService snsService) {
        this.snsService = snsService;
    }

    public String acceptCustomerDetails(CreateCustomerRequest request) {
        String uniqueID = UUID.randomUUID().toString();
        CreateCustomerResponse createCustomerResponse = new CreateCustomerResponse();
        createCustomerResponse.setUniqueId(uniqueID);
        request.setUniqueId(uniqueID);
        snsService.publishCustomerDetails(request);
        return createCustomerResponse.toString();
    }
}
