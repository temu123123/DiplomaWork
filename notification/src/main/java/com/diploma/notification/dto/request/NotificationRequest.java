package com.diploma.notification.dto.request;

import com.diploma.notification.entity.NotificationChannel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record NotificationRequest(
        @NotNull(message = "Recipient ID не может быть пустым")
        UUID recipientId,

        String recipientEmail,

        @NotBlank(message = "Тема уведомления не может быть пустой")
        String subject,

        @NotBlank(message = "Содержание уведомления не может быть пустым")
        String content,

        @NotNull(message = "Канал уведомления должен быть указан")
        NotificationChannel channel
) {}
