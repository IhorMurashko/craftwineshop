package com.craftWine.shop.enumTypes;

import lombok.Getter;

@Getter
public enum WineColor {
    WHITE("White"), PINK("Pink"), RED("Red"), ORANGE("Orange");

    private final String name;

    WineColor(String name) {
        this.name = name;
    }

}
