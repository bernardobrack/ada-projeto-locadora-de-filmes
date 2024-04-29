package com.ada.group3.locadoradefilmes.exception;

public class UsuarioJaExisteException extends RuntimeException{

    public UsuarioJaExisteException(){
        super("Username ja cadastrado");
    }

}
