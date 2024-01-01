package com.craftWine.shop.enumTypes;

import lombok.Getter;

@Getter
public enum SugarConsistency {
    SWEET("Sweet"), SEMI_SWEET("Semi sweet"), DRY("Dry"), SEMI_DRY("Semi dry");

    private final String name;

    SugarConsistency(String name) {
        this.name = name;
    }

}

