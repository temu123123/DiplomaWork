package com.diploma.notification.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.UUID;

@Document(collection = "notifications")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Notification {

    @Id
    private String id;

    private UUID recipientId;
    private String recipientEmail;
    private String subject;
    private String content;
    private NotificationChannel channel;
    private NotificationStatus status;
    private LocalDateTime sentAt;
    private LocalDateTime createdAt;

}
