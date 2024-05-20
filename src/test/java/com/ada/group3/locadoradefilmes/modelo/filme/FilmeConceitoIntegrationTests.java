package com.ada.group3.locadoradefilmes.modelo.filme;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class FilmeConceitoIntegrationTests {

    @Autowired
    private FilmeConceitoController controller;

    @Autowired
    private FilmeConceitoRepository repository;

    @Test
    @Transactional
    public void testCreateFilmeConceito() {
        FilmeConceitoRequest request = new FilmeConceitoRequest();
        request.setNome("Nome");
        request.setGenero("Genero");
        request.setDescricao("Descricao");

        FilmeConceitoDto response = controller.adicionarFilmeConceito(request);
        assertNotNull(response);
        assertEquals("Nome", response.getNome());
    }

    @Test
    @Transactional
    public void testBuscarFilmeConceitoPorUUID() {
        UUID uuid = UUID.randomUUID();
        FilmeConceito filme = new FilmeConceito(1L, uuid, "Nome", "Genero", "Descricao");
        repository.save(filme);

        FilmeConceitoDto response = controller.buscarPorUUID(uuid);
        assertNotNull(response);
        assertEquals(uuid, response.getUuid());
    }

    @Test
    @Transactional
    public void testAtualizarFilmeConceito() {
        UUID uuid = UUID.randomUUID();
        FilmeConceito filme = new FilmeConceito(1L, uuid, "Nome Antigo", "Genero Antigo", "Descricao Antiga");
        repository.save(filme);

        FilmeConceitoDto updatedDto = new FilmeConceitoDto(uuid, "Nome Novo", "Genero Novo", "Descricao Nova");
        controller.atualizar(uuid, updatedDto);

        FilmeConceito atualizado = repository.findByUuid(uuid).orElse(null);
        assertNotNull(atualizado);
        assertEquals("Nome Novo", atualizado.getNome());
    }

    @Test
    @Transactional
    public void testExcluirFilmeConceito() {
        UUID uuid = UUID.randomUUID();
        FilmeConceito filme = new FilmeConceito(1L, uuid, "Nome", "Genero", "Descricao");
        repository.save(filme);

        controller.excluir(uuid);
        Optional<FilmeConceito> excluido = repository.findByUuid(uuid);
        assertFalse(excluido.isPresent());
    }
}
