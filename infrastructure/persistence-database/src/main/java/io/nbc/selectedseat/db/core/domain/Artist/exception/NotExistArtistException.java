package io.nbc.selectedseat.db.core.domain.Artist.exception;

public class NotExistArtistException extends RuntimeException {

    public NotExistArtistException(String message) {
        super(message);
    }
}
