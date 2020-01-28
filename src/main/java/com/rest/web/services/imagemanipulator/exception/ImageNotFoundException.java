package com.rest.web.services.imagemanipulator.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ImageNotFoundException extends Exception {
    public ImageNotFoundException(String message) {
        super(message);
    }
}
