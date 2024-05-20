package com.ada.group3.locadoradefilmes.modelo.filme;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

public class FilmeConceitoTests {

    @Test
    public void testConstrutores() {
        UUID uuid = UUID.randomUUID();
        FilmeConceito filme = new FilmeConceito(1L, uuid, "Nome Antigo", "Genero Antigo", "Descrição Antiga");
        assertNotNull(filme);
        assertEquals(1L, filme.getId());
        assertEquals(uuid, filme.getUuid());
        assertEquals("Nome Antigo", filme.getNome());
        assertEquals("Genero Antigo", filme.getGenero());
        assertEquals("Descrição Antiga", filme.getDescricao());
    }

    @Test
    public void testConstrutorSemGenero() {
        UUID uuid = UUID.randomUUID();
        FilmeConceito filme = new FilmeConceito(1L, uuid, "Nome", "Descrição");
        assertNotNull(filme);
        assertEquals(1L, filme.getId());
        assertEquals(uuid, filme.getUuid());
        assertEquals("Nome", filme.getNome());
        assertNull(filme.getGenero()); // Verifica se o gênero é nulo
        assertEquals("Descrição", filme.getDescricao());
    }

    @Test
    public void testGettersAndSetters() {
        FilmeConceito filme = new FilmeConceito();
        filme.setId(1L);
        UUID uuid = UUID.randomUUID();
        filme.setUuid(uuid);
        filme.setNome("Nome");
        filme.setGenero("Genero");
        filme.setDescricao("Descricao");

        assertEquals(1L, filme.getId());
        assertEquals(uuid, filme.getUuid());
        assertEquals("Nome", filme.getNome());
        assertEquals("Genero", filme.getGenero());
        assertEquals("Descricao", filme.getDescricao());
    }

    @Test
    public void testFilmesReais() {
        FilmeConceito filme = new FilmeConceito();
        FilmeReal filmeReal1 = new FilmeReal();
        FilmeReal filmeReal2 = new FilmeReal();
        filme.setFilmesReais(List.of(filmeReal1, filmeReal2));

        assertNotNull(filme.getFilmesReais());
        assertEquals(2, filme.getFilmesReais().size());
        assertSame(filmeReal1, filme.getFilmesReais().get(0));
        assertSame(filmeReal2, filme.getFilmesReais().get(1));
    }

    @Test
    public void testAdicionarFilmeReal() {
        FilmeConceito filme = new FilmeConceito();
        FilmeReal filmeReal = new FilmeReal();
        filme.setFilmesReais(new ArrayList<>());
        filme.getFilmesReais().add(filmeReal);

        assertNotNull(filme.getFilmesReais());
        assertEquals(1, filme.getFilmesReais().size());
        assertSame(filmeReal, filme.getFilmesReais().get(0));
    }

    @Test
    public void testRemoverFilmeReal() {
        FilmeConceito filme = new FilmeConceito();
        FilmeReal filmeReal = new FilmeReal();
        filme.setFilmesReais(new ArrayList<>(List.of(filmeReal)));
        filme.getFilmesReais().remove(filmeReal);

        assertNotNull(filme.getFilmesReais());
        assertTrue(filme.getFilmesReais().isEmpty());
    }

    @Test
    public void testToString() {
        UUID uuid = UUID.randomUUID();
        FilmeConceito filme = new FilmeConceito(1L, uuid, "Nome", "Genero", "Descricao");
        String expected = "FilmeConceito{id=1, uuid=" + uuid + ", nome='Nome', genero='Genero', descricao='Descricao'}";
        assertEquals(expected, filme.toString());
    }
}
