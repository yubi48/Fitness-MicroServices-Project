package com.fitness.activityservice.dto;

import com.fitness.activityservice.model.ActivityType;

import java.time.LocalDateTime;
import java.util.Map;

public record ActivityResponse(
        String id,
        String userId,
        ActivityType type,
        Integer duration,
        Integer caloriesBurned,
        LocalDateTime startTime,
        Map<String, Object> additionalMetrics,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}

