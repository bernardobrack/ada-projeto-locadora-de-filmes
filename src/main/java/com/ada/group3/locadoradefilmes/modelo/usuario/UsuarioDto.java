package com.ada.group3.locadoradefilmes.modelo.usuario;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDto implements Serializable {

    private String nome;
    private String ultimoNome;
    private String cpf;
    private String email;
    private String login;
    private String senha;
    private Boolean ativo;
    private Boolean temAtraso;
}
