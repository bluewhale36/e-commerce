package com.guncat.ecommerce.product.dto;

import lombok.*;

@Getter
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class ProdPicDTO {

    private Long prodPicCode;
    private String picProdCode;
    private String prodPicName;
    private String prodPicCaption;
}
