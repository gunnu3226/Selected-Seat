package io.nbc.selectedseat.domain.artist.dto;

import lombok.Getter;

@Getter
public class CreateArtistResponseDTO {

    public Long id;

    public CreateArtistResponseDTO(final Long id) {
        this.id = id;
    }
}