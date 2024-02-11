package com.craftWine.shop.notificationSender;

import com.craftWine.shop.enumTypes.NotificationType;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationSenderFactoryImpl implements NotificationSenderFactory {

    private final ApplicationContext context;

    @Override
    public NotificationSender factory(NotificationType notificationType) {

        return switch (notificationType) {
            case SMS -> context.getBean(SmsNotificationSenderImpl.class);
            default -> context.getBean(EmailNotificationSenderImpl.class);
        };


    }
}
