package com.diploma.notification.repository;

import com.diploma.notification.entity.Notification;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface NotificationRepository extends MongoRepository<Notification, String> {

    List<Notification> findByRecipientId(UUID recipientId);

    List<Notification> findByRecipientIdOrderByCreatedAtDesc(UUID recipientId);

}
