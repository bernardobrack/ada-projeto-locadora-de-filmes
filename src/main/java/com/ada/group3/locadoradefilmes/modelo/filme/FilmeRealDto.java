package com.ada.group3.locadoradefilmes.modelo.filme;


import java.util.UUID;

public record FilmeRealDto(
        UUID id,
        FilmeConceitoDto filme,
        boolean isAlugado
) {
}
