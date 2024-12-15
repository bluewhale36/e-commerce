package com.guncat.ecommerce.product.dto;

import com.guncat.ecommerce.common.enums.IsEnabled;
import com.guncat.ecommerce.product.enums.ProdCategory;
import com.guncat.ecommerce.product.enums.ProdKind;
import com.guncat.ecommerce.product.enums.ProdStatus;
import lombok.*;

import java.util.List;

@Getter
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class ProductDTO {

    private String prodCode;
    private String registeredAdminCode;
    private ProdStatus prodStatus;
    private Long prodCnt;
    private ProdCategory prodCategory;
    private ProdKind prodKind;
    private String prodName;
    private Long prodPrice;
    private Long discntRate;
    private IsEnabled isEnabled;
    private List<ProdPicDTO> prodPicList;
    private List<ProdContDTO> prodContList;

}
