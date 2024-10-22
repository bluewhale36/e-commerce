package com.guncat.ecommerce.common.enums;

import lombok.Getter;

@Getter
public enum EventStatus {
    PENDING("처리 대기"),
    PROCESSING("처리 중"),
    COMPLETED("완료"),
    FAILED("실패"),
    CANCELLED("취소"),
    REJECTED("반려");

    private final String message;

    EventStatus(String message) {
        this.message = message;
    }
}
