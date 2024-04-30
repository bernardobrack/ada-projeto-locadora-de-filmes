package com.ada.group3.locadoradefilmes.exception;

public class FilmeRealNaoEncontradoException extends NaoEncontradoException {
    public FilmeRealNaoEncontradoException() {
        super("Filme real nao encontrado!");
    }
}
