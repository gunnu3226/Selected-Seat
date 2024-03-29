package io.nbc.selectedseat.web.domain.category.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CategoryRequestDTO(
    @NotBlank(message = "카테고리 이름을 입력해 주세요")
    String name

) {

}
