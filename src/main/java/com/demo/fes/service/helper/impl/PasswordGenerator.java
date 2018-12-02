package com.demo.fes.service.helper.impl;

import java.util.Random;

public class PasswordGenerator {
    private static final String ALPHA_CAPS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String NUMERIC = "0123456789";
    private static Random random = new Random();

    public static String generatePassword( ) {
        String result = "";
        String dic = ALPHA_CAPS + NUMERIC;

        for (int i = 0; i < 6; i++) {
            int index = random.nextInt(dic.length());
            result += dic.charAt(index);
        }
        return result;
    }
}
