package com.ada.group3.locadoradefilmes.modelo.filme;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class FilmeConceitoTest {
    private FilmeConceito filmeConceito;

    @BeforeEach
    void setUp() {
        filmeConceito = new FilmeConceito();
        filmeConceito.setNome("Filme Teste");
        filmeConceito.setGenero("Ação");
        filmeConceito.setDescricao("Descrição do filme teste");
        filmeConceito.setUuid(UUID.randomUUID());
        filmeConceito.setId(1L);
    }

    @Test
    void testGetId() {
        assertEquals(1L, filmeConceito.getId());
    }

    @Test
    void testGetUuid() {
        assertNotNull(filmeConceito.getUuid());
    }

    @Test
    void testGetNome() {
        assertEquals("Filme Teste", filmeConceito.getNome());
    }

    @Test
    void testGetGenero() {
        assertEquals("Ação", filmeConceito.getGenero());
    }

    @Test
    void testGetDescricao() {
        assertEquals("Descrição do filme teste", filmeConceito.getDescricao());
    }

    @Test
    void testGetFilmesReais() {
        List<FilmeReal> filmesReais = new ArrayList<>();
        filmeConceito.setFilmesReais(filmesReais);
        assertEquals(filmesReais, filmeConceito.getFilmesReais());
    }
}
