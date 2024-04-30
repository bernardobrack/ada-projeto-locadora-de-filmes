package com.ada.group3.locadoradefilmes.exception;

public class AluguelNaoEncontradoException extends NaoEncontradoException{
    public AluguelNaoEncontradoException(){
        super("Aluguel nao encontrado");
    }
}
