package com.craftWine.shop.utils;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.stream.IntStream;

public class SwitchCaseToCapitalize {

    public static String switchCaseToCapitalize(@NotNull String word) {

        char[] chars = word.toCharArray();


        chars[0] = Character.toUpperCase(chars[0]);


        for (int i = 1; i < chars.length; i++) {

            chars[i] = Character.toLowerCase(chars[i]);
        }




        return new String(chars);
    }

}
