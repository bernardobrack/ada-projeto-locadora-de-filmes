package com.ada.group3.locadoradefilmes.modelo.filme;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class FilmeConceitoRepositoryTests {

    @Autowired
    private FilmeConceitoRepository repository;

    @Test
    public void testFindByUuid() {
        UUID uuid = UUID.randomUUID();
        FilmeConceito filme = new FilmeConceito(1L, uuid, "Nome", "Descricao");
        repository.save(filme);

        Optional<FilmeConceito> resultado = repository.findByUuid(uuid);
        assertTrue(resultado.isPresent());
        assertEquals(filme, resultado.get());
    }

    @Test
    public void testFindByNomeContainingIgnoreCase() {
        FilmeConceito filme = new FilmeConceito(1L, UUID.randomUUID(), "Nome", "Descricao");
        repository.save(filme);

        List<FilmeConceito> resultado = repository.findByNomeContainingIgnoreCase("Nome");
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Nome", resultado.get(0).getNome());
    }
}
