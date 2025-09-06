package com.fitness.activityservice.service;

import com.fitness.activityservice.dto.ActivityRequest;
import com.fitness.activityservice.dto.ActivityResponse;
import com.fitness.activityservice.model.Activity;
import com.fitness.activityservice.repository.ActivityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ActivityService {

    private final ActivityRepository activityRepository;
    private final UserValidationService userValidationService;
    private final KafkaTemplate<String, Activity> kafkaTemplate;

    @Value("${kafka.topic.name}")
    private String topicName;


    public ActivityResponse trackActivity(ActivityRequest request) {
        log.info("Calling User Service for {}", request.userId());
        boolean isValid = userValidationService.validateUser(request.userId());
        if(!isValid) {
            throw new RuntimeException("Invalid User: " + request.userId());
        }
        Activity activity = Activity.builder()
                .userId(request.userId())
                .type(request.type())
                .duration(request.duration())
                .caloriesBurned(request.caloriesBurned())
                .startTime(request.startTime())
                .additionalMetrics(request.additionalMetrics())
                .build();

        Activity savedActivity = activityRepository.save(activity);
        try{
            log.info("called kafka");
            kafkaTemplate.send(topicName, savedActivity.getUserId(), savedActivity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mapToResponse(savedActivity);
    }


    private ActivityResponse mapToResponse(Activity activity) {
        return new ActivityResponse(
                activity.getId(),
                activity.getUserId(),
                activity.getType(),
                activity.getDuration(),
                activity.getCaloriesBurned(),
                activity.getStartTime(),
                activity.getAdditionalMetrics(),
                activity.getCreatedAt(),
                activity.getUpdatedAt()
        );
    }

}
