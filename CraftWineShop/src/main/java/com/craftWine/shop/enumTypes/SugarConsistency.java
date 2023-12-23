package com.craftWine.shop.enumTypes;

import lombok.Getter;

@Getter
public enum SugarConsistency {
    SWEET("Sweet"), SEMI_SWEET("Semi sweet"), SEMI_DRY("Semi dry"), DRY("Dry");

    private final String name;

    SugarConsistency(String name) {
        this.name = name;
    }

}

