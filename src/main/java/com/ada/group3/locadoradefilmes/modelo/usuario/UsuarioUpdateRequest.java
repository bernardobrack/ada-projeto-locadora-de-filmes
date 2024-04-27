package com.ada.group3.locadoradefilmes.modelo.usuario;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class UsuarioUpdateRequest  {
        private String username;
        private String password;

}
