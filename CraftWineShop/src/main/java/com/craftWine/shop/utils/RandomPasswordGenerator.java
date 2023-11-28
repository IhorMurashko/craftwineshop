package com.craftWine.shop.utils;
//
//import java.security.SecureRandom;
//
//public class RandomPasswordGenerator {
//
//    private static final String LOWERCASE_LETTERS = "abcdefghijklmnopqrstuvwxyz";
//    private static final String UPPERCASE_LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
//    private static final String DIGITS = "0123456789";
//    private static final String SPECIAL_CHARACTERS = "$-_";
//
//
//    public static String generateRandomPassword() {
//        SecureRandom random = new SecureRandom();
//        StringBuilder password = new StringBuilder();
//
//        // Генерация по одному символу из каждой категории
//        password.append(LOWERCASE_LETTERS.charAt(random.nextInt(LOWERCASE_LETTERS.length())));
//        password.append(UPPERCASE_LETTERS.charAt(random.nextInt(UPPERCASE_LETTERS.length())));
//        password.append(DIGITS.charAt(random.nextInt(DIGITS.length())));
//        password.append(SPECIAL_CHARACTERS.charAt(random.nextInt(SPECIAL_CHARACTERS.length())));
//
//        // Генерация оставшихся символов
//        for (int i = 4; i < 8; i++) {
//            int category = random.nextInt(4);
//            switch (category) {
//                case 0:
//                    password.append(LOWERCASE_LETTERS.charAt(random.nextInt(LOWERCASE_LETTERS.length())));
//                    break;
//                case 1:
//                    password.append(UPPERCASE_LETTERS.charAt(random.nextInt(UPPERCASE_LETTERS.length())));
//                    break;
//                case 2:
//                    password.append(DIGITS.charAt(random.nextInt(DIGITS.length())));
//                    break;
//                case 3:
//                    password.append(SPECIAL_CHARACTERS.charAt(random.nextInt(SPECIAL_CHARACTERS.length())));
//                    break;
//            }
//        }
//
//        // Перемешивание символов в пароле
//
//        return shuffleString(password.toString());
//    }
//
//    public static String shuffleString(String input) {
//        SecureRandom random = new SecureRandom();
//        char[] characters = input.toCharArray();
//        for (int i = characters.length - 1; i > 0; i--) {
//            int index = random.nextInt(i + 1);
//            char temp = characters[i];
//            characters[i] = characters[index];
//            characters[index] = temp;
//        }
//        return new String(characters);
//    }
//
//
//}

import java.security.SecureRandom;

public class RandomPasswordGenerator {

    private static final String LOWERCASE_LETTERS = "abcdefghijklmnopqrstuvwxyz";
    private static final String UPPERCASE_LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String DIGITS = "0123456789";
    private static final String SPECIAL_CHARACTERS = "$-_";

    /**
     * Generates a random password.
     *
     * @return A randomly generated password.
     */
    public static String generateRandomPassword() {
        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder();

        // Generate one character from each category
        password.append(LOWERCASE_LETTERS.charAt(random.nextInt(LOWERCASE_LETTERS.length())));
        password.append(UPPERCASE_LETTERS.charAt(random.nextInt(UPPERCASE_LETTERS.length())));
        password.append(DIGITS.charAt(random.nextInt(DIGITS.length())));
        password.append(SPECIAL_CHARACTERS.charAt(random.nextInt(SPECIAL_CHARACTERS.length())));

        // Generate the remaining characters
        for (int i = 4; i < 8; i++) {
            int category = random.nextInt(4);
            switch (category) {
                case 0:
                    password.append(LOWERCASE_LETTERS.charAt(random.nextInt(LOWERCASE_LETTERS.length())));
                    break;
                case 1:
                    password.append(UPPERCASE_LETTERS.charAt(random.nextInt(UPPERCASE_LETTERS.length())));
                    break;
                case 2:
                    password.append(DIGITS.charAt(random.nextInt(DIGITS.length())));
                    break;
                case 3:
                    password.append(SPECIAL_CHARACTERS.charAt(random.nextInt(SPECIAL_CHARACTERS.length())));
                    break;
            }
        }

        // Shuffle the characters in the password
        return shuffleString(password.toString());
    }

    /**
     * Shuffles the characters in a given string.
     *
     * @param input The input string to be shuffled.
     * @return The shuffled string.
     */
    public static String shuffleString(String input) {
        SecureRandom random = new SecureRandom();
        char[] characters = input.toCharArray();
        for (int i = characters.length - 1; i > 0; i--) {
            int index = random.nextInt(i + 1);
            char temp = characters[i];
            characters[i] = characters[index];
            characters[index] = temp;
        }
        return new String(characters);
    }
}

