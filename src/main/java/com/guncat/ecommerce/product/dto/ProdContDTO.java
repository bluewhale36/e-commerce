package com.guncat.ecommerce.product.dto;

import lombok.*;

@Getter
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class ProdContDTO {

    private Long prodContCode;
    private String contProdCode;
    private String prodContName;
}
