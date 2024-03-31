package io.nbc.selectedseat.domain.member.facade.dto;

import io.nbc.selectedseat.domain.artist.model.Artist;

public record FollowArtistsInfo(
    Long artistId,
    String name,
    String profile
) {

    public static FollowArtistsInfo from(final Artist artist) {
        return new FollowArtistsInfo(
            artist.getArtistId(),
            artist.getName(),
            artist.getProfile()
        );
    }
}
