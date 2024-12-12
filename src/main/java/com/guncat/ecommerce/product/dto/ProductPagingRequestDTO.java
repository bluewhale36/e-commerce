package com.guncat.ecommerce.product.dto;

import com.guncat.ecommerce.common.dto.AbstractPagingRequestDTO;

public class ProductPagingRequestDTO extends AbstractPagingRequestDTO {


    public ProductPagingRequestDTO(Integer pageNum, String searchType, String searchKeyword,
                                   String filterType, String[] filterValue, String sortingType) {
        super(pageNum, searchType, searchKeyword, filterType, filterValue, sortingType);
    }

    @Override
    protected String defaultSearchType() {
        return "prodName";
    }

    @Override
    protected String defaultSearchKeyword() {
        return "";
    }

    @Override
    protected String defaultSortingType() {
        return "prodName";
    }
}
