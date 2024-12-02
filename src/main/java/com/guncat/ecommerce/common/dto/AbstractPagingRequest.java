package com.guncat.ecommerce.common.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.ToString;

/**
 * 페이징, 검색, 정렬 등 데이터 조회 요청 시 사용되는 DTO 의 추상 클래스.
 */
@Getter
@ToString
public abstract class AbstractPagingRequest {

    private final int pageNum;

    private final String searchType;
    private final String searchKeyword;

    private final String sortingType;

    public AbstractPagingRequest(Integer pageNum, String searchType, String searchKeyword, String sortingType) {
        this.pageNum = pageNum != null ? pageNum : 0;
        this.searchType = searchType != null ? searchType : defaultSearchType();
        this.searchKeyword = searchKeyword != null ? searchKeyword : defaultSearchKeyword();
        this.sortingType = sortingType != null ? sortingType : defaultSortingType();
    }

    protected abstract String defaultSearchType();
    protected abstract String defaultSearchKeyword();
    protected abstract String defaultSortingType();
}
