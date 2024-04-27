package com.ada.group3.locadoradefilmes.modelo.aluguel;

import com.ada.group3.locadoradefilmes.modelo.filme.FilmeReal;
import com.ada.group3.locadoradefilmes.modelo.usuario.Usuario;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AluguelDTO {
    private UUID uuid;
    private LocalDateTime horarioAluguel;
    private LocalDateTime horarioDevolucao;
    private String usuarioLogin;
    private UUID filmeUuid;

}
