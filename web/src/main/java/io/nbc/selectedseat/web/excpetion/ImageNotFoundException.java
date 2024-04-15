package io.nbc.selectedseat.web.excpetion;

public class ImageNotFoundException extends RuntimeException {

    public ImageNotFoundException(String message) {
        super(message);
    }
}
