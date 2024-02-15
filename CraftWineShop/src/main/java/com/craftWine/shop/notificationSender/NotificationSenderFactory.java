package com.craftWine.shop.notificationSender;

import com.craftWine.shop.enumTypes.NotificationType;

public interface NotificationSenderFactory {

    NotificationSender factory(NotificationType notificationType);

}
