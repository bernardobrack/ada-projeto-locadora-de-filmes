package com.ada.group3.locadoradefilmes.modelo.filme;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class FilmeConceitoControllerTests {

    @Mock
    private FilmeConceitoService service;

    @InjectMocks
    private FilmeConceitoController controller;

    @Test
    public void testListarTodos() {
        List<FilmeConceitoDto> filmes = List.of(new FilmeConceitoDto(UUID.randomUUID(), "Nome", "Genero", "Descricao"));
        when(service.listarTodos()).thenReturn(filmes);

        List<FilmeConceitoDto> resultado = controller.listarTodos();
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Nome", resultado.get(0).getNome());
    }

    @Test
    public void testBuscarPorUUID() {
        UUID uuid = UUID.randomUUID();
        FilmeConceitoDto filme = new FilmeConceitoDto(uuid, "Nome", "Genero", "Descricao");
        when(service.buscarPorUUID(uuid)).thenReturn(filme);

        FilmeConceitoDto resultado = controller.buscarPorUUID(uuid);
        assertNotNull(resultado);
        assertEquals(uuid, resultado.getUuid());
        assertEquals("Nome", resultado.getNome());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testAdicionarFilmeConceito() {
        FilmeConceitoRequest request = new FilmeConceitoRequest();
        request.setNome("Nome");
        request.setGenero("Genero");
        request.setDescricao("Descricao");

        FilmeConceitoDto filme = new FilmeConceitoDto(UUID.randomUUID(), "Nome", "Genero", "Descricao");
        when(service.adicionarFilmeConceito(request)).thenReturn(filme);

        FilmeConceitoDto resultado = controller.adicionarFilmeConceito(request);
        assertNotNull(resultado);
        assertEquals("Nome", resultado.getNome());
    }
}
