package com.guncat.ecommerce.product.enums;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public enum ProdCategory {
    TOP("상의",
            Arrays.asList(
                    ProdKind.SWEATSHIRT, ProdKind.SHIRTS, ProdKind.TOP_HOODIE, ProdKind.SWEATER,
                    ProdKind.COLLAR, ProdKind.LONG_SLEEVE, ProdKind.HALF_SLEEVE, ProdKind.ETC_TOP
            )
    ),
    PANTS("하의",
            Arrays.asList(
                    ProdKind.DENIM_PANTS, ProdKind.COTTON_PANTS, ProdKind.CASUAL_PANTS, ProdKind.JOGGER_PANTS,
                    ProdKind.SHORT_PANTS, ProdKind.LEGGINGS, ProdKind.JUMP_SUIT, ProdKind.ETC_PANTS
            )
    ),
    ONEPIECE("원피스",
            Arrays.asList(
                    ProdKind.MINI_ONE, ProdKind.MED_ONE, ProdKind.MAX_ONE
            )
    ),
    SKIRT("스커트",
            Arrays.asList(
                    ProdKind.MINI_SKRT, ProdKind.MED_SKRT, ProdKind.LONG_SKRT
            )
    ),
    OUTER("아우터",
            Arrays.asList(
                    ProdKind.OUTER_HOODIE, ProdKind.BLOUSON, ProdKind.LEATHER, ProdKind.MUSTANG, ProdKind.TRUCKER,
                    ProdKind.SUIT, ProdKind.CARDIGAN, ProdKind.ANORAK, ProdKind.FLEECE, ProdKind.STADIUM,
                    ProdKind.ATMN_COAT, ProdKind.WIN_SIN_COAT, ProdKind.WIN_DUB_COAT, ProdKind.WIN_ETC_COAT,
                    ProdKind.LONG_PADDED, ProdKind.SHORT_PADDED, ProdKind.PADDED_VEST, ProdKind.HUNTING,
                    ProdKind.NYLON, ProdKind.ETC_OUTER
            )
    ),
    TRAINING("트레이닝",
            Arrays.asList(
                    ProdKind.TRN_TOP, ProdKind.TRN_BTM, ProdKind.TRN_ONE, ProdKind.TRN_SKRT,
                    ProdKind.TRN_SET, ProdKind.SWIM, ProdKind.SPT_SHOES, ProdKind.SPT_BAG, ProdKind.SPT_CAP
            )
    ),
    ACCESSORY("액세서리",
            Arrays.asList(
                    ProdKind.RING, ProdKind.BRACELET, ProdKind.NECKLACE, ProdKind.EAR_RING
            )
    ),
    SHOES("신발",
            Arrays.asList(
                    ProdKind.SNEAKERS, ProdKind.FORMAL, ProdKind.SLIPPER, ProdKind.BOOTS, ProdKind.SHOE_ITEM
            )
    ),
    BAG("가방",
            Arrays.asList(
                    ProdKind.BACKPACK, ProdKind.CROSS, ProdKind.SHOULDER, ProdKind.TOTE, ProdKind.REUSABLE,
                    ProdKind.DUFFEL, ProdKind.WAIST, ProdKind.POUCH, ProdKind.BRIFF, ProdKind.SUITCASE,
                    ProdKind.BAG_ITEM, ProdKind.WALLET, ProdKind.CLUTCH
            )
    ),
    ITEM("패션 소품",
            Arrays.asList(
                    ProdKind.HAT, ProdKind.SOCKS, ProdKind.GLASSES, ProdKind.BELT, ProdKind.WATCH, ProdKind.ETC_ITEM
            )
    ),
    INNER("이너",
            Arrays.asList(
                ProdKind.INN_TOP, ProdKind.INN_BTM, ProdKind.INN_SET, ProdKind.HOMEWEAR
            )
    ),
    EMPTY("분류 없음", Collections.EMPTY_LIST);

    private final String categoryInKorean;
    private final List<ProdKind> prodKindList;

    ProdCategory(String categoryInKorean, List<ProdKind> prodKindList) {
        this.categoryInKorean = categoryInKorean;
        this.prodKindList = prodKindList;
    }

    public String getCategoryInKorean() {
        return categoryInKorean;
    }

    public ProdCategory findByProdKind(ProdKind prodKind) {
        return Arrays.stream(ProdCategory.values())
                .filter(group -> group.hasProdKind(prodKind))
                .findAny()
                .orElse(EMPTY);
    }

    public boolean hasProdKind(ProdKind prodKind) {
        return prodKindList.stream()
                .anyMatch(kind -> kind == prodKind);
    }
}
