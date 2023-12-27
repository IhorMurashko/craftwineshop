package com.craftWine.shop.utils;


/**
 * Utility class for transforming the case of a word to capitalize the first letter and make the rest lowercase.
 */
public class SwitchCaseToCapitalize {

    /**
     * Transforms the case of a word to capitalize the first letter and make the rest lowercase.
     *
     * @param word The input word to be transformed.
     * @return The word with the first letter capitalized and the rest in lowercase.
     * If the input word is empty, it is returned as it is.
     * @throws NullPointerException If the input word is {@code null}.
     */
    public static String switchCaseToCapitalize(String word) {
        if (word.isEmpty()) {
            // If the input word is empty, return it as is
            return word;
        }

        char[] chars = word.toCharArray();

        // Capitalize the first character
        chars[0] = Character.toUpperCase(chars[0]);

        // Convert the remaining characters to lowercase
        for (int i = 1; i < chars.length; i++) {
            chars[i] = Character.toLowerCase(chars[i]);
        }

        // Return the modified string
        return String.valueOf(chars);
    }
}

