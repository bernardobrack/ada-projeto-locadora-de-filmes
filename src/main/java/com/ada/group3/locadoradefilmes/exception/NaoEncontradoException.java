package com.ada.group3.locadoradefilmes.exception;

import org.springframework.http.HttpStatus;

public abstract class NaoEncontradoException extends AbstractException {

    protected NaoEncontradoException(String message) {
        super(message);
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.NOT_FOUND;
    }
}