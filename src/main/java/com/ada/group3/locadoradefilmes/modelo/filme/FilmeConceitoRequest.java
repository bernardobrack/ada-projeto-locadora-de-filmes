package com.ada.group3.locadoradefilmes.modelo.filme;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.br.CPF;

@Getter
@Setter
@ToString
public class FilmeConceitoRequest {
    //Incluir número
    @Pattern(regexp = "^[a-zA-Z0-9 ]+${1,100}",message = "Nome deve conter letras de A ate Z e/ou números entre 1 e 100 caracteres")
    private String nome;
    @Pattern(regexp = "^[a-zA-Z0-9 ]+${1,100}",message = "Nome deve conter letras de A ate Z e/ou números entre 1 e 100 caracteres")
    private String genero;
   @NotBlank()
   @Size(min = 0, max = 144, message = "Descrição deve conter letras de A ate Z e/ou números entre 1 e 144 caracteres")
   private String descricao;

}