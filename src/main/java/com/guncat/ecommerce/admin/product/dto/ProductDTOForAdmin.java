package com.guncat.ecommerce.admin.product.dto;

import com.guncat.ecommerce.common.enums.IsEnabled;
import com.guncat.ecommerce.product.dto.ProductDTO;
import com.guncat.ecommerce.product.enums.ProdCategory;
import com.guncat.ecommerce.product.enums.ProdStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@ToString
public class ProductDTOForAdmin {

    private ProductDTO productDTO;
    private String currentAdminUserId;
    private List<ProdCategory> prodCategoryList;
    private List<ProdStatus> prodStatusList;
    private List<IsEnabled> isEnabledList;

}
