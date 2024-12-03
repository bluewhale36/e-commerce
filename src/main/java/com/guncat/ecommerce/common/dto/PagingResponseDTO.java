package com.guncat.ecommerce.common.dto;


import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class PagingResponseDTO<T extends List<?>> {

    // 조회 결과
    private final T content;

    /*
        페이징 관련 변수
     */
    // 현재 content 의 element 개수
    private long totalElements;
    // 전체 페이지 개수
    private final long totalPages;
    // 현재 페이지 번호
    private int currentPage = 0;

    private boolean hasContent;
    private boolean isFirst;
    private boolean isLast;
    private boolean hasNext;
    private boolean hasPrevious;

    /*
        검색 관련 변수
     */
    // 검색 유형
    private final String searchType;
    // 검색어
    private final String searchKeyword;

    /*
        정렬 관련 변수
     */
    // 정렬 유형
    private final String sortingType;


    public PagingResponseDTO(T content, long totalPages, int currentPage,
                             String searchType, String searchKeyword, String sortingType) {
        this.content = content;
        this.totalPages = totalPages;
        this.currentPage = currentPage;

        this.searchType = searchType;
        this.searchKeyword = searchKeyword;

        this.sortingType = sortingType;

        setPagingStatus();
    }

    private void setPagingStatus() {
        totalElements = content != null ? content.size() : 0L;
        hasContent = content != null && !content.isEmpty();
        isFirst = currentPage == 0;
        isLast = currentPage + 1 == totalPages;
        hasPrevious = currentPage > 0;
        hasNext = currentPage + 1 < totalPages;
    }

}
