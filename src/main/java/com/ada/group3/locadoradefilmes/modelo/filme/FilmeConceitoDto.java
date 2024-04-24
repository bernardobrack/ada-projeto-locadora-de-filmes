package com.ada.group3.locadoradefilmes.modelo.filme;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FilmeConceitoDto {

    private String nome;
    private String genero;
    private String descricao;
}
