package com.craftWine.shop.enumTypes;

import lombok.Getter;

@Getter
public enum WineColor {
    WHITE("Біле"), PINK("Рожеве"), RED("Червоне"), ORANGE("Помаранчеве");

    private final String name;

    WineColor(String name) {
        this.name = name;
    }

}
