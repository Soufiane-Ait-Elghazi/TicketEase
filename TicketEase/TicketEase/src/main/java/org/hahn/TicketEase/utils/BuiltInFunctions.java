package org.hahn.TicketEase.utils;

import java.util.Random;

public class BuiltInFunctions {

    private static final Random random = new Random();

    public static String generateRandomSixDigit() {
        int number = random.nextInt(1_000_000);
        return String.format("%06d", number);
    }

}
