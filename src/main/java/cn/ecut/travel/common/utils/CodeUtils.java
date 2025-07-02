package cn.ecut.travel.common.utils;

import java.util.Random;

public class CodeUtils {
    public static String generateRandomNumberCode(int length) {
        String numbers = "0123456789";
        Random random = new Random();
        StringBuilder code = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(numbers.length());
            code.append(numbers.charAt(index));
        }

        return code.toString();
    }
}
