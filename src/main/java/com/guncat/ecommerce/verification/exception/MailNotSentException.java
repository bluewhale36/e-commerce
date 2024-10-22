package com.guncat.ecommerce.verification.exception;

/**
 * 메일을 전송하지 못한 경우 발생하는 예외.
 */
public class MailNotSentException extends RuntimeException {
    public MailNotSentException(String message) {
        super(message);
    }
}
