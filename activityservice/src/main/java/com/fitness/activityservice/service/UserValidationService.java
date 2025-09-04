package com.fitness.activityservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserValidationService {

    private final WebClient userServiceWebClient;

    public Boolean validateUser(String userId) {
        log.info("Calling User Service for {}", userId);
        try {
            return userServiceWebClient.get()
                    .uri("http://localhost:8081/api/users/{userId}/validate", userId)
                    .retrieve()
                    .bodyToMono(Boolean.class)
                    .block();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        return false;
    }
}
