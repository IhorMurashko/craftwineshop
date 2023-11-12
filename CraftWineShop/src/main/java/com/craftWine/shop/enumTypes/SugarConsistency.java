package com.craftWine.shop.enumTypes;

import lombok.Getter;

@Getter
public enum SugarConsistency {
    SWEET("Солодке"), SEMI_SWEET("Напівсолодке"), SEMI_DRY("Напівсухе"), DRY("Сухе");

    private final String name;

    SugarConsistency(String name) {
        this.name = name;
    }

}

