package com.guncat.ecommerce.users.domain.vo;

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
