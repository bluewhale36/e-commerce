package com.guncat.ecommerce.verification.exception;

/**
 * 세션에 저장된 인증 코드가 일치하지 않을 경우 발생하는 예외.
 */
public class CodeNotMatchException extends RuntimeException {
    public CodeNotMatchException(String message) {
        super(message);
    }
}
