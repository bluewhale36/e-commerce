package com.guncat.ecommerce.product.enums;

import lombok.Getter;

@Getter
public enum ProdStatus {
    ON_SALE("판매 중"),
    RESTOCK("재입고 대기"),
    SOLD_OUT("품절");

    private final String description;

    ProdStatus(String description) {
        this.description = description;
    }
}
