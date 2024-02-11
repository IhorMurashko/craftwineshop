package com.craftWine.shop.notificationSender;

public interface NotificationSenderStrategy {
    void sendNotification(NotificationSender notificationSender, Notification notification);
}
