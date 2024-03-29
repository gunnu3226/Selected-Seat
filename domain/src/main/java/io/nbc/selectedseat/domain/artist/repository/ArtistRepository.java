package io.nbc.selectedseat.domain.artist.repository;

import io.nbc.selectedseat.domain.artist.model.Artist;

public interface ArtistRepository {

    Artist save(Artist artist);
}
