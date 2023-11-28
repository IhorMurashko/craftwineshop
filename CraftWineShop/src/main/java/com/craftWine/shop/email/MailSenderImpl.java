package com.craftWine.shop.email;

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
public class MailSenderImpl implements EmailSender {

    private final static Logger LOGGER = LoggerFactory.getLogger(MailSenderImpl.class);

    private final JavaMailSender mailSender;

    /**
     * Sends an email asynchronously.
     *
     * @param to      The email address of the recipient.
     * @param email   The content of the email.
     * @param subject The subject of the email.
     * @throws IllegalStateException If an error occurs while sending the email.
     */
    @Override
    @Async
    public void send(String to, String email, String subject) {
        try {
            // Create a MimeMessage
            MimeMessage mimeMessage = mailSender.createMimeMessage();

            // Use MimeMessageHelper to set email properties
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setText(email, true);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setFrom("i.murasko0911@gmail.com");

            // Send the email
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            // Log the error and throw an exception if sending fails
            LOGGER.error("Failed to send email", e);
            throw new IllegalStateException("Failed to send email");
        }
    }
}

