package com.guncat.ecommerce.users.domain.vo;

/**
 * 비밀번호 값 저장의 VO Record Class.<br/>
 * 비밀번호 값의 유효성을 판단한다.
 * @param password 비밀번호 문자열
 */
public record PasswordVO(String password) {

    public PasswordVO(String password) {
        if (isPasswordValid(password)) {
            this.password = password;
        } else {
            throw new IllegalArgumentException("잘못된 비밀번호 형식입니다.");
        }
    }

    private boolean isPasswordValid(String pswd) {
        String PASSWORD_PATTERN = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[\\d])[a-zA-Z]{1}[a-zA-Z\\d\\!\\@\\*\\?\\~]{7,15}$";
        return !pswd.isBlank() && pswd.matches(PASSWORD_PATTERN);
    }
}
