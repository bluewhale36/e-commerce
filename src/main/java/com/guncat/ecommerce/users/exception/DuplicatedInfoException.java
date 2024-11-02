package com.guncat.ecommerce.users.exception;

/**
 * 아이디 또는 이메일 이 중복된 상태로 회원 가입을 시도할 경우 발생하는 예외.
 */
public class DuplicatedInfoException extends RuntimeException {
    public DuplicatedInfoException(String message) {
        super(message);
    }
}
