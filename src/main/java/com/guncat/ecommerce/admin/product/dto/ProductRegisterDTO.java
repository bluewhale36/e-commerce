package com.guncat.ecommerce.admin.product.dto;

import com.guncat.ecommerce.common.enums.IsEnabled;
import com.guncat.ecommerce.product.enums.ProdCategory;
import com.guncat.ecommerce.product.enums.ProdKind;
import com.guncat.ecommerce.product.enums.ProdStatus;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class ProductRegisterDTO {

    private String registeredAdminCode;
    private ProdStatus prodStatus;
    private Long prodCnt;
    private ProdCategory prodCategory;
    private ProdKind prodKind;
    private String prodName;
    private Long prodPrice;
    private Long discntRate;
    private IsEnabled isEnabled;
    private MultipartFile[] prodCont;
    private MultipartFile[] prodPic;

}
