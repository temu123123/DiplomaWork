package com.diploma.notification.service;

import com.diploma.notification.dto.request.NotificationRequest;
import com.diploma.notification.dto.response.NotificationResponse;
import com.diploma.notification.entity.Notification;
import com.diploma.notification.entity.NotificationChannel;
import com.diploma.notification.entity.NotificationStatus;
import com.diploma.notification.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class NotificationService {

    private final NotificationRepository repository;
    private final JavaMailSender mailSender;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public NotificationResponse sendNotification(NotificationRequest request) {
        Notification notification = new Notification();
        notification.setRecipientId(request.recipientId());
        notification.setRecipientEmail(request.recipientEmail());
        notification.setSubject(request.subject());
        notification.setContent(request.content());
        notification.setChannel(request.channel());
        notification.setStatus(NotificationStatus.PENDING);
        notification.setCreatedAt(LocalDateTime.now());

        try {
            send(notification);
            notification.setStatus(NotificationStatus.SENT);
            notification.setSentAt(LocalDateTime.now());
        } catch (Exception e) {
            log.error("Failed to send notification: {}", e.getMessage());
            notification.setStatus(NotificationStatus.FAILED);
        }

        Notification saved = repository.save(notification);

        // Отправка события в Kafka
        kafkaTemplate.send("notification.sent", saved.getId(), saved);

        return mapToResponse(saved);
    }

    public List<NotificationResponse> getNotificationsByRecipient(UUID recipientId) {
        return repository.findByRecipientIdOrderByCreatedAtDesc(recipientId).stream()
                .map(this::mapToResponse)
                .toList();
    }

    private void send(Notification notification) {
        switch (notification.getChannel()) {
            case EMAIL -> sendEmail(notification);
            case SMS -> sendSms(notification);
            case PUSH -> sendPush(notification);
            case IN_APP -> sendInApp(notification);
        }
    }

    private void sendEmail(Notification notification) {
        if (notification.getRecipientEmail() == null || notification.getRecipientEmail().isBlank()) {
            throw new IllegalArgumentException("Email address is required for email notification");
        }

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(notification.getRecipientEmail());
        message.setSubject(notification.getSubject());
        message.setText(notification.getContent());
        mailSender.send(message);
        log.info("Email sent to: {}", notification.getRecipientEmail());
    }

    private void sendSms(Notification notification) {
        // TODO: Интеграция с SMS провайдером
        log.warn("SMS sending not implemented yet");
    }

    private void sendPush(Notification notification) {
        // TODO: Интеграция с push notification сервисом
        log.warn("Push notification not implemented yet");
    }

    private void sendInApp(Notification notification) {
        // TODO: Сохранение в in-app уведомления
        log.warn("In-app notification not implemented yet");
    }

    private NotificationResponse mapToResponse(Notification notification) {
        return new NotificationResponse(
                notification.getId(),
                notification.getRecipientId(),
                notification.getRecipientEmail(),
                notification.getSubject(),
                notification.getContent(),
                notification.getChannel(),
                notification.getStatus(),
                notification.getSentAt(),
                notification.getCreatedAt()
        );
    }
}
