package com.guncat.ecommerce.verification.exception;

/**
 * 인증 정보를 찾을 수 없을 경우 발생하는 예외.
 */
public class VerificationInfoNotFoundException extends RuntimeException {
    public VerificationInfoNotFoundException(String message) {
      super(message);
    }
}
