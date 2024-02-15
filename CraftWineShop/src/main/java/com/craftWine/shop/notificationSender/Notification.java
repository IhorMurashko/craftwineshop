package com.craftWine.shop.notificationSender;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
public class Notification {
    @NotBlank(message = "addressee cant be blank")
    private String addressee;
    @NotBlank(message = "message cant be blank")
    private String text;
    private String subject;
}
