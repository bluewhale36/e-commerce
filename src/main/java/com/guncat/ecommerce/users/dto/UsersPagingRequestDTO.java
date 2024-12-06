package com.guncat.ecommerce.users.dto;

import com.guncat.ecommerce.common.dto.AbstractPagingRequest;
import lombok.Getter;
import lombok.ToString;

/**
 * 사용자 관련 데이터 조회 요청 시 사용되는 DTO 클래스.
 *
 * @see AbstractPagingRequest
 */
public class UsersPagingRequestDTO extends AbstractPagingRequest {

    public UsersPagingRequestDTO(Integer pageNum, String searchType, String searchKeyword,
                                 String filterType, String[] filterValue, String sortingType) {
        super(pageNum, searchType, searchKeyword, filterType, filterValue, sortingType);
    }

    @Override
    protected String defaultSearchType() {
        return "userId";
    }

    @Override
    protected String defaultSearchKeyword() {
        return "";
    }

    @Override
    protected String defaultSortingType() {
        return "userId";
    }
}
