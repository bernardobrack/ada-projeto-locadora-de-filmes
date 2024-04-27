package com.ada.group3.locadoradefilmes.modelo.usuario;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.br.CPF;

@Getter
@Setter
@ToString
public class UsuarioRequest {
    @Pattern(regexp = "^[a-zA-Z]+${3,20}",message = "Nome deve conter letra de A ate Z sem espaco entre 3 e 20 caracteres")
    private String name;
    @Pattern(regexp = "^[a-zA-Z]+${3,20}",message = "ultimo nome deve conter letra de A ate Z sem espaco entre 3 e 20 caracteres")
    private String lastName;
    @CPF
    private String cpf;
    @Email
    private String email;
    @Pattern(regexp = "[\\w.]{5,20}", message = "Username deve ser alfanumérico entre 5 e 20 caracteres (lowercase, uppercase, numbers, _, .)")
    private String username;
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$",
            message = "A senha deve conter de 8 a 20 caracteres (lowercase, uppercase, numbers, special, no-sequences)")
    private String password;
    private Role role;



}