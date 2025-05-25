package com.mdv.appstore.dto.response;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaginationResponse<T> {
    @Builder.Default
    private List<T> content = new ArrayList<>();

    @Builder.Default
    private PaginationMetadata pagination = PaginationMetadata.builder().build();

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PaginationMetadata {
        @Builder.Default
        private int currentPage = 1;

        @Builder.Default
        private int pageSize = 10;

        @Builder.Default
        private long totalItems = 0;

        @Builder.Default
        private int totalPages = 0;
    }

    public static <T> PaginationResponse<T> of(List<T> content, long totalItems, int currentPage, int pageSize) {
        return PaginationResponse.<T>builder()
                .content(content)
                .pagination(PaginationMetadata.builder()
                        .totalItems(totalItems)
                        .currentPage(currentPage)
                        .pageSize(pageSize)
                        .totalPages((int) Math.ceil((double) totalItems / pageSize))
                        .build())
                .build();
    }

}