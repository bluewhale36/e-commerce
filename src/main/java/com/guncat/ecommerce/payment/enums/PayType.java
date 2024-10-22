package com.guncat.ecommerce.payment.enums;

import lombok.Getter;

@Getter
public enum PayType {
    ACCOUNT_TRANSFER("계좌이체"),
    REMITTANCE("무통장입금"),
    CARD("신용카드"),
    KAKAO("카카오페이"),
    NAVER("네이버페이"),
    TOSS("토스페이"),
    PAYCO("페이코");

    private final String description;

    PayType(String description) {
        this.description = description;
    }
}
