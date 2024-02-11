package com.craftWine.shop.notificationSender;

import org.springframework.stereotype.Service;

@Service
public class NotificationSenderStrategyImpl implements NotificationSenderStrategy {

    @Override
    public void sendNotification(NotificationSender notificationSender, Notification notification) {
        notificationSender.send(notification);
    }
}
