package com.guncat.ecommerce.users.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class UserExceptionHandler {

    /**
     * {@link UserNotFoundException} 의 handler.<br/>
     * {@link HttpStatus#UNAUTHORIZED} 를 나타낸다.
     * @param e {@link UserNotFoundException}
     * @return {@link HttpStatus#UNAUTHORIZED}
     */
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFoundException(UserNotFoundException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }

    @ExceptionHandler(DuplicatedInfoException.class)
    public ResponseEntity<String> handleDuplicatedInfoException(DuplicatedInfoException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

}
