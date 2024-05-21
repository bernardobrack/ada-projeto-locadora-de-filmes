package com.ada.group3.locadoradefilmes.modelo.filme;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class FilmeConceitoControllerTest {

    @Mock
    private FilmeConceitoService service;

    @InjectMocks
    private FilmeConceitoController controller;

    @Test
    public void testListarTodos() {
    }

    @Test
    public void testConsultarPorNome() {
    }

    @Test
    public void testConsultarPorGenero() {
    }

    @Test
    public void testConsultarPorTituloEGenero() {
    }

    @Test
    public void testBuscarPorUUID() {
    }

    @Test
    public void testAdicionarFilmeConceito() {
    }

    @Test
    public void testAtualizar() {
    }

    @Test
    public void testAtualizarFilmeInexistente() {
        UUID uuid = UUID.randomUUID();
        FilmeConceitoDto filmeConceitoAtualizado = new FilmeConceitoDto(uuid, "Filme 1", "Ação", "Nova Descrição");

        controller.atualizar(uuid, filmeConceitoAtualizado);

        verify(service).atualizar(uuid, filmeConceitoAtualizado);
    }

    @Test
    public void testExcluir() {
    }

    @Test
    public void testExcluirFilmeInexistente() {
        UUID uuid = UUID.randomUUID();

        controller.excluir(uuid);

        verify(service).excluir(uuid);
    }
}
