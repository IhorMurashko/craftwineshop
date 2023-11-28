package com.craftWine.shop.email;

public interface EmailSender {

    void send(String to, String email, String subject);
}
