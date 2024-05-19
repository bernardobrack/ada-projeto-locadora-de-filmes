package com.ada.group3.locadoradefilmes.modelo.filme;
import com.ada.group3.locadoradefilmes.modelo.aluguel.Aluguel;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class FilmeConceitoServiceTests {

    @Mock
    private FilmeConceitoRepository filmeConceitoRepositoryMock;

    @Mock
    private org.modelmapper.ModelMapper modelMapperMock;

    @InjectMocks
    private FilmeConceitoService filmeConceitoService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void deveAtualizarFilmeConceitoComSucesso() {
        // Cenário
        UUID uuid = UUID.randomUUID();
        FilmeConceitoDto filmeConceitoDtoAtualizado = new FilmeConceitoDto(1L, "Novo Nome", "Nova Descrição");
        FilmeConceito filmeConceitoEncontrado = new FilmeConceito(1L, uuid, "Nome Antigo", "Descrição Antiga");

        when(filmeConceitoRepositoryMock.findByUuid(uuid)).thenReturn(Optional.of(filmeConceitoEncontrado));
        when(modelMapperMock.map(filmeConceitoDtoAtualizado, FilmeConceito.class)).thenReturn(filmeConceitoEncontrado);

        // Ação
        filmeConceitoService.atualizar(uuid, filmeConceitoDtoAtualizado);

        // Verificação
        verify(filmeConceitoRepositoryMock).findByUuid(uuid);
        verify(modelMapperMock).map(filmeConceitoDtoAtualizado, FilmeConceito.class);
        verify(filmeConceitoRepositoryMock).save(filmeConceitoEncontrado);
    }
}
