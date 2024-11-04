package com.guncat.ecommerce.verification.util;

import java.util.Random;

/**
 * 회원 가입, 본인 인증 등에서 사용되는 인증코드 생성을 위한 Util Class.
 */
public class VerificationCode {

    /**
     * 인증코드를 생성 및 반환하는 method.
     * @return 영어 대문자 및 숫자의 조합으로 이루어진 7자리 문자열.
     */
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
