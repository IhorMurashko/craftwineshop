package com.craftWine.shop.enumTypes;

import lombok.Getter;

@Getter
public enum NotificationType {

    EMAIL("email"), SMS("sms");
    private final String name;

    NotificationType(String name) {
        this.name = name;
    }

}
