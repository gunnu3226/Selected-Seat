package io.nbc.selectedseat.domain.category.dto;

import io.nbc.selectedseat.domain.category.model.Category;

public record GetCategoryResponseDTO(
    Long categoryId,
    String name
) {

    public static GetCategoryResponseDTO from(Category category) {
        return new GetCategoryResponseDTO(category.getCategoryId(), category.getName());
    }
}
