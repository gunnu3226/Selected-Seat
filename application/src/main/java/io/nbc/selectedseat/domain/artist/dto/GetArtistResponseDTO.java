package io.nbc.selectedseat.domain.artist.dto;

import io.nbc.selectedseat.domain.artist.model.Artist;

public record GetArtistResponseDTO(
    Long artistId,
    String name,
    String profile
) {

    public static GetArtistResponseDTO from(Artist artist) {
        return new GetArtistResponseDTO(
            artist.getArtistId(),
            artist.getName(),
            artist.getProfile()
        );
    }
}
