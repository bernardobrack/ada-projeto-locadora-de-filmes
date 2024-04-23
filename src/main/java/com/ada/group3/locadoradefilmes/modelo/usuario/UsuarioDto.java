package com.ada.group3.locadoradefilmes.modelo.usuario;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDto {

    private String nome;
    private String sobrenome;
    private String cpf;
    private String email;
    private String login;
    private Boolean ativo;
    private Boolean temAtraso;
}
