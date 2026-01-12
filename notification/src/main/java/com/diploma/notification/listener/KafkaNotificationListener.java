package com.diploma.notification.listener;

import com.diploma.notification.dto.request.NotificationRequest;
import com.diploma.notification.entity.NotificationChannel;
import com.diploma.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaNotificationListener {

    private final NotificationService notificationService;

    @KafkaListener(topics = "risk.updated", groupId = "notification-service-group")
    public void handleRiskUpdated(Object riskData) {
        log.info("Received risk.updated event: {}", riskData);
        // TODO: Парсинг данных и создание уведомления
        // Пока заглушка
    }

    @KafkaListener(topics = "attendance.critical", groupId = "notification-service-group")
    public void handleAttendanceCritical(Object attendanceData) {
        log.info("Received attendance.critical event: {}", attendanceData);
        // TODO: Создание уведомления о критических пропусках
    }

    @KafkaListener(topics = "grade.low", groupId = "notification-service-group")
    public void handleGradeLow(Object gradeData) {
        log.info("Received grade.low event: {}", gradeData);
        // TODO: Создание уведомления о низкой оценке
    }
}
