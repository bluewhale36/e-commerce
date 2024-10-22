package com.guncat.ecommerce.users.exception;

public class DuplicatedInfoException extends RuntimeException {
    public DuplicatedInfoException(String message) {
        super(message);
    }
}
