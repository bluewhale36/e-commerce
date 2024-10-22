package com.guncat.ecommerce.verification.util;

import java.util.Random;

public class VerificationCode {

    public static String generateCode() {
        StringBuffer code = new StringBuffer();
        Random r = new Random();

        for (int i = 0; i < 7; i++) {
            int num = r.nextInt(10);
            if (num < 4) {
                code.append((char) (r.nextInt(26) + 65));
            } else {
                code.append(r.nextInt(10));
            }
        }

        return code.toString();
    }

}
