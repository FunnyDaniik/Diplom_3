package ru.praktikum.utils;

import java.util.Random;

public class UserGenerator {
    private static final String CHARS = "abcdefghijklmnopqrstuvwxyz";
    private static final Random random = new Random();

    public static String generateName() {
        return "user_" + randomString(5);
    }

    public static String generateEmail() {
        return "test_" + randomString(5) + "@example.com";
    }

    public static String generatePassword(int length) {
        return randomString(length);
    }

    private static String randomString(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(CHARS.charAt(random.nextInt(CHARS.length())));
        }
        return sb.toString();
    }
}
