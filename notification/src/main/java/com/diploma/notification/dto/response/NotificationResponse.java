package com.diploma.notification.dto.response;

import com.diploma.notification.entity.NotificationChannel;
import com.diploma.notification.entity.NotificationStatus;
import java.time.LocalDateTime;
import java.util.UUID;

public record NotificationResponse(
        String id,
        UUID recipientId,
        String recipientEmail,
        String subject,
        String content,
        NotificationChannel channel,
        NotificationStatus status,
        LocalDateTime sentAt,
        LocalDateTime createdAt
) {}
