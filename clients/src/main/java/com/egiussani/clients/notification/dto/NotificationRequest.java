package com.egiussani.clients.notification.dto;

import lombok.Builder;

@Builder
public record NotificationRequest(
        Integer toCustomerId,
        String toCustomerName,
        String message
) {
}
