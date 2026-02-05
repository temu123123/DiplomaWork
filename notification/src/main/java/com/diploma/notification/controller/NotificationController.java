package com.diploma.notification.controller;

import com.diploma.notification.dto.request.NotificationRequest;
import com.diploma.notification.dto.response.NotificationResponse;
import com.diploma.notification.service.NotificationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasAnyRole('TEACHER', 'ADMIN', 'DEAN')")
    public NotificationResponse sendNotification(@RequestBody @Valid NotificationRequest request) {
        return service.sendNotification(request);
    }

    @GetMapping("/recipient/{recipientId}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('STUDENT', 'TEACHER', 'ADMIN', 'DEAN')")
    public List<NotificationResponse> getNotificationsByRecipient(@PathVariable UUID recipientId) {
        return service.getNotificationsByRecipient(recipientId);
    }
}
