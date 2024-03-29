package io.nbc.selectedseat.domain.artist.dto;

import io.nbc.selectedseat.domain.artist.model.Artist;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GetArtistResponseDTO {

    public Long artist_id;
    public String name;
    public String profile;

    public GetArtistResponseDTO(Artist artist) {
        this.artist_id = artist.getArtistId();
        this.name = artist.getName();
        this.profile = artist.getProfile();
    }
}
