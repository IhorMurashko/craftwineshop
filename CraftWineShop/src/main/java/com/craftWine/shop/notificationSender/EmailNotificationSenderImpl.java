package com.craftWine.shop.notificationSender;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * Implementation of the {@code EmailSender} interface for sending emails asynchronously.
 */
@Service
@AllArgsConstructor
public class EmailNotificationSenderImpl implements NotificationSender {

    private final static Logger LOGGER = LoggerFactory.getLogger(EmailNotificationSenderImpl.class);

    private final JavaMailSender mailSender;

    /**
     * Sends a text asynchronously.
     *
     * @param notification the object contains all the information about notification like addressee,
     *                     message text, subject.
     * @throws IllegalStateException If an error occurs while sending the text.
     */
    @Override
    @Async
    public void send(Notification notification) {
        try {
            // Create a MimeMessage
            MimeMessage mimeMessage = mailSender.createMimeMessage();

            // Use MimeMessageHelper to set text properties
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setText(notification.getText(), true);
            helper.setTo(notification.getAddressee());
            helper.setSubject(notification.getSubject());
            helper.setFrom("krswqq@gmail.com");

            // Send the text
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            // Log the error and throw an exception if sending fails
            LOGGER.error("Failed to send text", e);
            throw new IllegalStateException("Failed to send text");
        }
    }
}

