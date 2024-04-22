package io.nbc.selectedseat.db.core.domain.artist.exception;

public class NotExistArtistException extends RuntimeException {

    public NotExistArtistException(String message) {
        super(message);
    }
}
