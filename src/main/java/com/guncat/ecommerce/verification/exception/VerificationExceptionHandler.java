package com.guncat.ecommerce.verification.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * 이메일 인증 관련 예외를 처리하는 Handler Class.
 *
 * @see CodeNotMatchException
 * @see VerificationInfoNotFoundException
 * @see MailNotSentException
 */
@ControllerAdvice
public class VerificationExceptionHandler {

    @ExceptionHandler(MailNotSentException.class)
    public ResponseEntity<String> handleMailNotSentException(MailNotSentException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }

    @ExceptionHandler(VerificationInfoNotFoundException.class)
    public ResponseEntity<String> handleVerificationInfoNotFoundException(VerificationInfoNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(CodeNotMatchException.class)
    public ResponseEntity<String> handleCodeNotMatchException(CodeNotMatchException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
}
