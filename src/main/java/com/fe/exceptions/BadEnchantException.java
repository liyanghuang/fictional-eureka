package com.fe.exceptions;


public class BadEnchantException extends RuntimeException {

    public BadEnchantException(final String errorMessage) {
        super(errorMessage);
    }
}