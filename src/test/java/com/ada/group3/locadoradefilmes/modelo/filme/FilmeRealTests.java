package com.ada.group3.locadoradefilmes.modelo.filme;

import com.ada.group3.locadoradefilmes.modelo.aluguel.Aluguel;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

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
        assertNull(filme.getId()); //
    }

    @Test
    public void testFilmeConceitoSetAndGet() {
        FilmeReal filme = new FilmeReal();
        FilmeConceito conceito = new FilmeConceito();
        filme.setFilmeConceito(conceito);
        assertSame(conceito, filme.getFilmeConceito());
    }

}