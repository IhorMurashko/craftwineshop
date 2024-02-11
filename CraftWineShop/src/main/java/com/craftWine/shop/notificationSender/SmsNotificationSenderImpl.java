package com.craftWine.shop.notificationSender;

import lombok.Getter;
import org.springframework.stereotype.Service;

@Service
public class SmsNotificationSenderImpl implements NotificationSender {
    @Override
    public void send(Notification notification) {
        throw new IllegalStateException("Function isn't implemented");
    }
}
