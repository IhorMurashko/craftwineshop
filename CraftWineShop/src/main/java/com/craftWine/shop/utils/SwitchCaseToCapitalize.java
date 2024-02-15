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

        // Split the input string into words using Unicode non-letter characters as delimiters
        String[] words = word.split("[^\\p{L}]");

        StringBuilder result = new StringBuilder();

        for (String w : words) {
            if (!w.isEmpty()) {
                // Capitalize the first character of each word and convert the rest to lowercase
                result.append(Character.toUpperCase(w.charAt(0)))
                        .append(w.substring(1).toLowerCase())
                        .append(" "); // Add a space after each word
            }
        }

        // Remove the trailing space and return the modified string
        return result.toString().trim();
    }
}

