package com.egiussani.clients.notification;

import com.egiussani.clients.notification.dto.NotificationRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "notification",
        url = "${clients.notification.url}"
)
public interface NotificationClient {

    @PostMapping(path = "api/v1/notification")
    void sendNotification(@RequestBody NotificationRequest notificationRequest);

}
