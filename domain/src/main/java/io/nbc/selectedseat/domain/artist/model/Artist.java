package io.nbc.selectedseat.domain.artist.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Artist {

    private Long artistId;
    private String name;
    private String profile;

    public Artist(String name, String profile) {
        this.name = name;
        this.profile = profile;
    }
}
