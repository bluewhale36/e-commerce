package com.guncat.ecommerce.users.domain.vo;

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
