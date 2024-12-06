package com.guncat.ecommerce.common.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.ToString;

import java.util.Arrays;
import java.util.List;

/**
 * 페이징, 검색, 정렬 등 데이터 조회 요청 시 사용되는 DTO 의 추상 클래스.
 */
@Getter
@ToString
public abstract class AbstractPagingRequest {

    private final int pageNum;

    private final String searchType;
    private final String searchKeyword;

    private final String filterType;
    private final String[] filterValue;

    private final String sortingType;

    private final boolean isSearching;
    private final boolean isFiltering;

    public AbstractPagingRequest(Integer pageNum, String searchType, String searchKeyword,
                                 String filterType, String[] filterValue, String sortingType) {
        this.pageNum = pageNum != null ? pageNum : 0;
        this.searchType = searchType != null && !searchType.isBlank() ? searchType : defaultSearchType();
        this.searchKeyword = searchKeyword != null && !searchKeyword.isBlank() ? searchKeyword : defaultSearchKeyword();
        this.filterType = filterType != null && !filterType.equals("select") ? filterType : "";
        this.filterValue = filterValue != null ? filterValue : new String[]{""};
        this.sortingType = sortingType != null && !sortingType.isBlank() ? sortingType : defaultSortingType();

        isSearching = !this.searchKeyword.equals(defaultSearchKeyword());
        isFiltering = !(this.filterValue.length == 1 && this.filterValue[0].isEmpty());
    }

    protected abstract String defaultSearchType();
    protected abstract String defaultSearchKeyword();
    protected abstract String defaultSortingType();
}
