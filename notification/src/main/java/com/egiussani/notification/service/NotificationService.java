package com.egiussani.notification.service;

import com.egiussani.clients.notification.dto.NotificationRequest;
import com.egiussani.notification.model.Notification;
import com.egiussani.notification.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public void send(NotificationRequest notificationRequest) {
        notificationRepository.save(
                Notification.builder()
                        .toCustomerId(notificationRequest.toCustomerId())
                        .toCustomerEmail(notificationRequest.toCustomerName())
                        .message(notificationRequest.message())
                        .sender("egiussani")
                        .sentAt(LocalDateTime.now())
                        .build()
        );
    }
}
