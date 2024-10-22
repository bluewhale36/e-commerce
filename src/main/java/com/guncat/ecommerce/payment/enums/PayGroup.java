package com.guncat.ecommerce.payment.enums;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 결제 종류에 대한 Enum Class.
 */
public enum PayGroup {
    CASH("현금",
            Arrays.asList(
                    PayType.ACCOUNT_TRANSFER, PayType.REMITTANCE
            )
    ),
    CARD("카드",
            Arrays.asList(
                    PayType.CARD, PayType.KAKAO, PayType.NAVER, PayType.TOSS, PayType.PAYCO
            )
    ),
    EMPTY("없음", Collections.EMPTY_LIST);

    private final String description;
    private final List<PayType> payTypeList;

    PayGroup(String description, List<PayType> payTypeList) {
        this.description = description;
        this.payTypeList = payTypeList;
    }

    public String getDescription() {
        return description;
    }

    /**
     * 결제 수단이 속한 결제 종류 반환.
     * @param payType 결제 수단.
     * @return {@link PayGroup} - 결제 종류.
     */
    public PayGroup findByPayType(PayType payType) {
        return Arrays.stream(PayGroup.values())
                .filter(group -> group.hasPayType(payType))
                .findAny()
                .orElse(EMPTY);
    }

    /**
     * 결제 수단이 결제 종류 중에 존재 하는 여부 반환.
     * @param payType 결제 수단.
     * @return {@code true} - 존재할 경우.<br/>
     * {@code false} - 존재하지 않을 경우.
     */
    public boolean hasPayType(PayType payType) {
        return payTypeList.stream()
                .anyMatch(type -> type == payType);
    }
}
