package io.nbc.selectedseat.domain.category.exception;

public class CategoryExistException extends RuntimeException {

    public CategoryExistException(final String message) {
        super(message);
    }
}
