package com.guncat.ecommerce.users.domain.vo;

/**
 * 아이디 값 저장의 VO Record Class.<br/>
 * 아이디 값의 유효성을 판단한다.
 * @param userId 아이디 문자열
 */
public record UserIdVO(String userId) {

    public UserIdVO(String userId) {
        if (isUserIdValid(userId)) {
            this.userId = userId;
        } else {
            throw new IllegalArgumentException("잘못된 아이디 형식입니다.");
        }
    }

    private boolean isUserIdValid(String userId) {
        String USER_ID_PATTERN = "^(?=.*[a-zA-Z])(?=.*\\d)[a-zA-Z]{1}[a-zA-Z\\d]{5,12}$";
        return !userId.isBlank() && userId.matches(USER_ID_PATTERN);
    }
}
