package dev.olatoye.onboardingservice.controller;

import dev.olatoye.onboardingservice.model.dto.request.CreateCustomerRequest;
import dev.olatoye.onboardingservice.service.OnboardingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.HashMap;
import java.util.Map;

@RestController
@EnableWebMvc
@RequestMapping("/customer")
public class OnboardingController {

    private final OnboardingService onboardingService;

    public OnboardingController(OnboardingService onboardingService) {
        this.onboardingService = onboardingService;
    }

    @GetMapping
    public ResponseEntity<?> ping() {
        Map<String, String> pong = new HashMap<>();
        pong.put("pong", "Hello, World!");
        return ResponseEntity.ok().body(pong);
    }

    @PostMapping
    public ResponseEntity<?> onboardCustomer(@RequestBody CreateCustomerRequest request) {
        return ResponseEntity.ok().body(onboardingService.acceptCustomerDetails(request));
    }
}
