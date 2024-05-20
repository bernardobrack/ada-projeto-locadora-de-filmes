package com.ada.group3.locadoradefilmes.modelo.filme;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class FilmeConceitoRequest {

    @Pattern(regexp = "^[a-zA-Z0-9 ]{1,100}$", message = "Nome deve conter letras de A a Z e/ou números entre 1 e 100 caracteres")
    private String nome;

    @Pattern(regexp = "^[a-zA-Z0-9 ]{1,100}$", message = "Genero deve conter letras de A a Z e/ou números entre 1 e 100 caracteres")
    private String genero;

    @NotBlank(message = "Descrição não pode estar em branco")
    @Size(min = 1, max = 144, message = "Descrição deve conter letras de A a Z e/ou números entre 1 e 144 caracteres")
    private String descricao;

    // Construtor que aceita todos os argumentos
    public FilmeConceitoRequest(String nome, String genero, String descricao) {
        this.nome = nome;
        this.genero = genero;
        this.descricao = descricao;
    }

    // Construtor padrão
    public FilmeConceitoRequest() {
    }
}
