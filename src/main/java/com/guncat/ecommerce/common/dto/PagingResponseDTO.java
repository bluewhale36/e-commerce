package com.guncat.ecommerce.common.dto;


import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class PagingResponseDTO<T extends List<?>> {

    private final T content;

    // 현재 content 의 element 개수
    private final long totalElements;
    // 전체 페이지 개수
    private final long totalPages;
    // 현재 페이지 번호
    private final int currentPage;

    private final boolean hasContent;
    private final boolean isFirst;
    private final boolean isLast;
    private final boolean hasNext;
    private final boolean hasPrevious;

    public PagingResponseDTO(T content, long totalPages, int currentPage) {
        this.content = content;
        this.totalPages = totalPages;
        this.currentPage = currentPage;

        this.totalElements = content != null ? content.size() : 0L;
        this.hasContent = content != null && !content.isEmpty();
        this.isFirst = currentPage == 0;
        this.isLast = currentPage + 1 == totalPages;
        this.hasPrevious = currentPage > 0;
        this.hasNext = currentPage + 1 < totalPages;
    }

}
