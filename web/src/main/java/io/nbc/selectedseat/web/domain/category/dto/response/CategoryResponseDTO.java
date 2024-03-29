package io.nbc.selectedseat.web.domain.category.dto.response;

public record CategoryResponseDTO(
    Long categoryId
) {

    public static CategoryResponseDTO from(Long categoryId) {
        return new CategoryResponseDTO(categoryId);
    }

}
