package com.ada.group3.locadoradefilmes.modelo.filme;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FilmeConceitoRequestTest {

    @Test
    public void testValidNome() {
        assertDoesNotThrow(() -> {
            FilmeConceitoRequest request = new FilmeConceitoRequest();
            request.setNome("Filme de Ação");
        });
    }

    @Test
    public void testValidGenero() {
        assertDoesNotThrow(() -> {
            FilmeConceitoRequest request = new FilmeConceitoRequest();
            request.setGenero("Ação");
        });
    }

    @Test
    public void testValidDescricao() {
        assertDoesNotThrow(() -> {
            FilmeConceitoRequest request = new FilmeConceitoRequest();
            request.setDescricao("Descrição do filme.");
        });
    }
}
