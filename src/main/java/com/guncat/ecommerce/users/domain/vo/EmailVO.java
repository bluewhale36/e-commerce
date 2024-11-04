package com.guncat.ecommerce.users.domain.vo;

/**
 * 이메일 값 저장의 VO Record Class.<br/>
 * 이메일 값의 유효성을 판단한다.
 * @param email 이메일 문자열
 */
public record EmailVO(String email) {

    public EmailVO(String email) {
        if (isEmailValid(email)) {
            this.email = email;
        } else {
            throw new IllegalArgumentException("잘못된 이메일 형식입니다.");
        }
    }

    private boolean isEmailValid(String email) {
        String EMAIL_PATTERN =
                "^(?=.{1,250})[0-9a-zA-Z]([-_\\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\\.]?[0-9a-zA-Z])*\\.[a-zA-Z]{2,3}$";
        return !email.isBlank() && email.matches(EMAIL_PATTERN);
    }

}
