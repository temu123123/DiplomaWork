package com.diploma.notification.controller;

import com.diploma.notification.dto.request.NotificationRequest;
import com.diploma.notification.dto.response.NotificationResponse;
import com.diploma.notification.service.NotificationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public NotificationResponse sendNotification(@RequestBody @Valid NotificationRequest request) {
        return service.sendNotification(request);
    }

    @GetMapping("/recipient/{recipientId}")
    @ResponseStatus(HttpStatus.OK)
    public List<NotificationResponse> getNotificationsByRecipient(@PathVariable UUID recipientId) {
        return service.getNotificationsByRecipient(recipientId);
    }
}
