package com.ada.group3.locadoradefilmes.modelo.filme;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import java.util.UUID;

public class FilmeConceitoDto {

    private Long id;
    private UUID uuid;
    private String nome;
    private String genero;
    private String descricao;
    private List<FilmeRealDto> filmesReais;

}