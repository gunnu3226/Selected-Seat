package io.nbc.selectedseat.domain.artist.repository;

import io.nbc.selectedseat.domain.artist.model.Artist;

public interface ArtistRepository {

    Artist save(Artist artist);

    Artist getArtist(final Long artistId);

    Long update(
        final Long artistId,
        final String name,
        final String profile
    );

    void delete(final Long artistId);
}
