package org.sopt.post.dto.response;


import org.springframework.data.domain.Page;

public record PageableInfo(
        int pageNumber,
        int pageSize,
        long totalElements,
        int totalPages,
        int numberOfElements,
        boolean isFirst,
        boolean isLast
) {
    public static PageableInfo of(Page<?> page) {
        return new PageableInfo(
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.getNumberOfElements(),
                page.isFirst(),
                page.isLast()
        );
    }
}
