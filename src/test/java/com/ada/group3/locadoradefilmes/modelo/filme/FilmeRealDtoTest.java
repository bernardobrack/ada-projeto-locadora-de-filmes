package com.ada.group3.locadoradefilmes.modelo.filme;

import org.junit.jupiter.api.Test;

import java.util.UUID;
import static org.junit.jupiter.api.Assertions.assertFalse;

class FilmeRealDtoTest {
    @Test
    public void criarFilmeRealDtoDeveGerarUmFilmDtoNaoAlugado() {

        UUID randomId = UUID.randomUUID();
        // Cenário
        FilmeConceitoDto mockFilmeConceitoDto = new FilmeConceitoDto(randomId, "Batman VS Thiago", "Ação", "Descrição do filme");

        //Ação
        FilmeRealDto filmeRealDto = new FilmeRealDto(randomId, mockFilmeConceitoDto, false);

        // Validação
        assertFalse(filmeRealDto.isAlugado());
    }

}