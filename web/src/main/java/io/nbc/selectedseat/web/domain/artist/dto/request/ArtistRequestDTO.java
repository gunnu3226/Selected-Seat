package io.nbc.selectedseat.web.domain.artist.dto.request;

import jakarta.validation.constraints.NotBlank;

public record ArtistRequestDTO(
    @NotBlank(message = "이름을 기입해주세요")
    String name,
    @NotBlank(message = "이미지를 넣어주세요")
    String profile
) {

}
