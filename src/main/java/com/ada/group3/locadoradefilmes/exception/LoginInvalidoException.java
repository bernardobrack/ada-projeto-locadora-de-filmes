package com.ada.group3.locadoradefilmes.exception;

public class LoginInvalidoException extends RuntimeException {

    public LoginInvalidoException() {
        super("Login inválido");
    }

    public LoginInvalidoException(String message) {
        super(message);
    }

}
