package com.ada.group3.locadoradefilmes.modelo.filme;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class FilmeConceitoRepositoryTest {

    @Autowired
    private FilmeConceitoRepository repository;

    @Test
    public void testFindByUuid() {
        UUID uuid = UUID.randomUUID();
        FilmeConceito filme = new FilmeConceito();
        filme.setUuid(uuid);
        repository.save(filme);

        Optional<FilmeConceito> optionalFilme = repository.findByUuid(uuid);

        assertTrue(optionalFilme.isPresent());
        assertEquals(uuid, optionalFilme.get().getUuid());
    }

    @Test
    public void testDeleteByUuid() {
        UUID uuid = UUID.randomUUID();
        FilmeConceito filme = new FilmeConceito();
        filme.setUuid(uuid);
        repository.save(filme);

        repository.deleteByUuid(uuid);

        Optional<FilmeConceito> optionalFilme = repository.findByUuid(uuid);
        assertTrue(optionalFilme.isEmpty());
    }


}
