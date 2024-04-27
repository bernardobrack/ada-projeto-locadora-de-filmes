package com.ada.group3.locadoradefilmes.modelo.usuario;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDto implements Serializable {

    private String name;
    private String lastName;
    private String cpf;
    private String email;
    private String username;
    private String password;
    private Role role;
    private Boolean active;
    private Boolean isLate;
}
