package com.ada.group3.locadoradefilmes.exception;

import org.springframework.http.HttpStatus;

public abstract class AbstractException extends RuntimeException {

    protected AbstractException(String message) {
        super(message);
    }

    public abstract HttpStatus getStatus();

}