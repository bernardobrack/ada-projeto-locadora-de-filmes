package com.ada.group3.locadoradefilmes.modelo.filme;

import com.ada.group3.locadoradefilmes.modelo.aluguel.Aluguel;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class FilmeRealTests {

    @Test
    public void ifInputDataCorrectly_ThenCreateAFilmeReal() {
        FilmeConceito filmeConceito = new FilmeConceito();
        List<Aluguel> lista = null;
        boolean isAlugado = true;
        FilmeReal filmeReal = new FilmeReal(1L,isAlugado, UUID.randomUUID(), filmeConceito, lista);
        assertInstanceOf(FilmeReal.class,filmeReal);
    }

    @Test
    public void testFilmeConceitoNotNull() {
        FilmeReal filme = new FilmeReal();
        filme.setFilmeConceito(new FilmeConceito());
        assertNotNull(filme.getFilmeConceito());
    }

    @Test
    public void testIdIsGenerated() {
        FilmeReal filme = new FilmeReal();
        assertNull(filme.getId());
    }

    @Test
    public void testFilmeConceitoSetAndGet() {
        FilmeReal filme = new FilmeReal();
        FilmeConceito conceito = new FilmeConceito();
        filme.setFilmeConceito(conceito);
        assertSame(conceito, filme.getFilmeConceito());
    }

    @Test
    public void deveriaRetornarIdCorretamente() {
        FilmeReal filmeReal = new FilmeReal();
        filmeReal.setId(1L);

        assertEquals(1L, filmeReal.getId());
    }

    @Test
    public void deveriaRetornarIsAlugadoCorretamente() {
        FilmeReal filmeReal = new FilmeReal();
        filmeReal.setAlugado(true);

        assertTrue(filmeReal.isAlugado());
    }

    @Test
    public void deveriaRetornarUuidCorretamente() {
        FilmeReal filmeReal = new FilmeReal();
        UUID uuid = UUID.randomUUID();
        filmeReal.setUuid(uuid);

        assertEquals(uuid, filmeReal.getUuid());
    }

    @Test
    public void deveriaRetornarFilmeConceitoCorretamente() {
        FilmeReal filmeReal = new FilmeReal();
        FilmeConceito filmeConceito = new FilmeConceito();
        filmeReal.setFilmeConceito(filmeConceito);

        assertEquals(filmeConceito, filmeReal.getFilmeConceito());
    }

    @Test
    public void deveriaRetornarAlugueisCorretamente() {
        FilmeReal filmeReal = new FilmeReal();
        List<Aluguel> alugueis = mock(List.class);
        filmeReal.setAlugueis(alugueis);

        assertEquals(alugueis, filmeReal.getAlugueis());
    }

    @Test
    public void deveriaSetarIdCorretamente() {
        FilmeReal filmeReal = new FilmeReal();
        filmeReal.setId(1L);

        assertEquals(1L, filmeReal.getId());
    }

    @Test
    public void deveriaSetarIsAlugadoCorretamente() {
        FilmeReal filmeReal = new FilmeReal();
        filmeReal.setAlugado(true);

        assertTrue(filmeReal.isAlugado());
    }

    @Test
    public void deveriaSetarUuidCorretamente() {
        FilmeReal filmeReal = new FilmeReal();
        UUID uuid = UUID.randomUUID();
        filmeReal.setUuid(uuid);

        assertEquals(uuid, filmeReal.getUuid());
    }

    @Test
    public void deveriaSetarFilmeConceitoCorretamente() {
        FilmeReal filmeReal = new FilmeReal();
        FilmeConceito filmeConceito = new FilmeConceito();
        filmeReal.setFilmeConceito(filmeConceito);

        assertEquals(filmeConceito, filmeReal.getFilmeConceito());
    }

    @Test
    public void deveriaSetarAlugueisCorretamente() {
        FilmeReal filmeReal = new FilmeReal();
        List<Aluguel> alugueis = mock(List.class);
        filmeReal.setAlugueis(alugueis);

        assertEquals(alugueis, filmeReal.getAlugueis());
    }

}