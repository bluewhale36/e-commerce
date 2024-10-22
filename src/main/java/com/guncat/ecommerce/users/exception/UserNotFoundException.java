package com.guncat.ecommerce.users.exception;

/**
 * 로그인 등에서 회원 정보를 찾을 수 없는 경우 발생하는 예외.
 *
 * @see java.lang.RuntimeException
 */
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
