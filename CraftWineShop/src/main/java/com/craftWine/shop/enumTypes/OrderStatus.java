package com.craftWine.shop.enumTypes;

import lombok.Getter;

@Getter
public enum OrderStatus {
    AWAITING_CONFIRMATION("В очікуванні підтвердження"),
    APPROVED("Підтверджено"),
    CANCELED("Скасовано"),
    SENT("Відправлено"),
    CLOSED("Закрито");

    private final String name;

    OrderStatus(String name) {
        this.name = name;
    }
}
