package com.diploma.notification.listener;

import com.diploma.notification.client.StudentGrpcClient;
import com.diploma.notification.dto.request.NotificationRequest;
import com.diploma.notification.entity.NotificationChannel;
import com.diploma.notification.service.NotificationService;
import com.diploma.student.grpc.StudentDto;
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
    private final StudentGrpcClient studentGrpcClient;
    private final com.fasterxml.jackson.databind.ObjectMapper objectMapper;

    @org.springframework.beans.factory.annotation.Value("${notification.test-email:}")
    private String testEmail;

    @KafkaListener(topics = "risk.updated", groupId = "notification-service-group")
    public void handleRiskUpdated(org.apache.kafka.clients.consumer.ConsumerRecord<?, ?> record) {
        log.info("Received risk.updated event: {}", record.value());
        try {
            java.util.Map<?, ?> data = convertToMap(record.value());
            String studentIdStr = String.valueOf(data.get("studentId"));
            String riskLevel = String.valueOf(data.get("riskLevel"));
            Double riskScore = data.get("riskScore") != null ? ((Number) data.get("riskScore")).doubleValue() : null;
            
            if (studentIdStr != null && !"null".equals(studentIdStr) && riskLevel != null && !"null".equals(riskLevel)) {
                UUID studentId = UUID.fromString(studentIdStr);
                
                // Fetch student details to get real email
                StudentDto student = studentGrpcClient.getStudent(studentId);
                String email = (student != null && !student.getEmail().isBlank()) ? student.getEmail() : getRecipientEmail(studentId);
                String studentName = (student != null) ? student.getFullName() : "Студент";

                String subject = "Обновление оценки академического риска";
                String content = generateRiskMessage(studentName, riskLevel, riskScore, data);
                
                NotificationRequest request = new NotificationRequest(
                    studentId, 
                    email, 
                    subject, 
                    content, 
                    NotificationChannel.EMAIL
                );
                notificationService.sendNotification(request);
            }
        } catch (Exception e) {
            log.error("Error processing risk.updated: {}", e.getMessage(), e);
        }
    }

    @KafkaListener(topics = "attendance.critical", groupId = "notification-service-group")
    public void handleAttendanceCritical(org.apache.kafka.clients.consumer.ConsumerRecord<?, ?> record) {
        log.info("Received attendance.critical event: {}", record.value());
        try {
             java.util.Map<?, ?> data = convertToMap(record.value());
             String studentIdStr = String.valueOf(data.get("studentId"));
             
             if (studentIdStr != null && !"null".equals(studentIdStr)) {
                 UUID studentId = UUID.fromString(studentIdStr);
                 StudentDto student = studentGrpcClient.getStudent(studentId);
                 String email = (student != null && !student.getEmail().isBlank()) ? student.getEmail() : getRecipientEmail(studentId);
                 
                 NotificationRequest request = new NotificationRequest(
                     studentId,
                     email,
                     "Критическое предупреждение о посещаемости",
                     "Уважаемый студент, количество ваших пропусков достигло критической отметки. Пожалуйста, свяжитесь с деканатом.",
                     NotificationChannel.EMAIL
                 );
                 notificationService.sendNotification(request);
             }
        } catch (Exception e) {
            log.error("Error processing attendance.critical: {}", e.getMessage(), e);
        }
    }

    @KafkaListener(topics = "grade.low", groupId = "notification-service-group")
    public void handleGradeLow(org.apache.kafka.clients.consumer.ConsumerRecord<?, ?> record) {
        log.info("Received grade.low event: {}", record.value());
        try {
             java.util.Map<?, ?> data = convertToMap(record.value());
             String studentIdStr = String.valueOf(data.get("studentId"));
             Double gradeValue = data.get("gradeValue") != null ? ((Number) data.get("gradeValue")).doubleValue() : null;
             
             if (studentIdStr != null && !"null".equals(studentIdStr)) {
                 UUID studentId = UUID.fromString(studentIdStr);
                 StudentDto student = studentGrpcClient.getStudent(studentId);
                 String email = (student != null && !student.getEmail().isBlank()) ? student.getEmail() : getRecipientEmail(studentId);

                 NotificationRequest request = new NotificationRequest(
                     studentId,
                     email,
                     "Уведомление о низкой оценке",
                     "Вы получили низкую оценку: " + (gradeValue != null ? gradeValue : "N/A") + ". Рекомендуем уделить больше внимания данному предмету.",
                     NotificationChannel.EMAIL
                 );
                 notificationService.sendNotification(request);
             }
        } catch (Exception e) {
            log.error("Error processing grade.low: {}", e.getMessage(), e);
        }
    }

    private String generateRiskMessage(String name, String level, Double score, java.util.Map<?, ?> data) {
        StringBuilder sb = new StringBuilder();
        sb.append("Здравствуйте, ").append(name).append("!\n\n");
        sb.append("Система мониторинга провела оценку вашего академического риска.\n");
        sb.append("Текущий уровень риска: ").append(translateRiskLevel(level)).append("\n");
        if (score != null) {
            sb.append("Балл риска: ").append(String.format("%.2f", score)).append("/100\n");
        }
        sb.append("\nПодробности оценки:\n");
        
        Object attendanceFactor = data.get("attendanceFactor");
        Object gradeFactor = data.get("gradeFactor");
        
        if (attendanceFactor != null) {
            sb.append("- Фактор посещаемости: ").append(attendanceFactor).append(" (влияет на 60% оценки)\n");
        }
        if (gradeFactor != null) {
            sb.append("- Фактор успеваемости: ").append(gradeFactor).append(" (влияет на 40% оценки)\n");
        }
        
        sb.append("\nРекомендации:\n");
        switch (level) {
            case "LOW" -> sb.append("Ваши показатели в норме. Продолжайте в том же духе!");
            case "MEDIUM" -> sb.append("Ваш риск оценивается как умеренный. Обратите внимание на посещаемость и старайтесь не пропускать занятия без уважительной причины.");
            case "HIGH" -> sb.append("Внимание! Высокий уровень риска. Мы заметили значительное количество пропусков или низкие оценки. Рекомендуем проконсультироваться с преподавателями.");
            case "CRITICAL" -> sb.append("Критический уровень риска! Существует серьезная угроза вашей успеваемости. Вам необходимо срочно явиться в деканат для выяснения ситуации.");
            default -> sb.append("Следите за своей успеваемостью и посещаемостью.");
        }
        
        sb.append("\n\nС уважением,\nУниверситетская система мониторинга.");
        return sb.toString();
    }

    private String translateRiskLevel(String level) {
        return switch (level) {
            case "LOW" -> "НИЗКИЙ";
            case "MEDIUM" -> "СРЕДНИЙ";
            case "HIGH" -> "ВЫСОКИЙ";
            case "CRITICAL" -> "КРИТИЧЕСКИЙ";
            default -> level;
        };
    }

    private String getRecipientEmail(UUID studentId) {
        if (testEmail != null && !testEmail.isBlank()) {
            return testEmail;
        }
        return "student_" + studentId + "@university.com";
    }

    private java.util.Map<?, ?> convertToMap(Object event) {
        if (event == null) return java.util.Collections.emptyMap();
        if (event instanceof java.util.Map) {
            return (java.util.Map<?, ?>) event;
        }
        return objectMapper.convertValue(event, java.util.Map.class);
    }
}
