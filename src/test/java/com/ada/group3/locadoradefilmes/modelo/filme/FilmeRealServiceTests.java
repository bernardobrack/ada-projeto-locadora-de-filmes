package com.ada.group3.locadoradefilmes.modelo.filme;

import com.ada.group3.locadoradefilmes.exception.FilmeConceitoNaoEncontradoException;
import com.ada.group3.locadoradefilmes.exception.FilmeRealNaoEncontradoException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class FilmeRealServiceTests {


    private FilmeRealRepository filmeRealRepository;

    private FilmeConceitoRepository filmeConceitoRepository;


    private FilmeRealService filmeRealService;

    private FilmeReal filmeReal;


    @BeforeEach
    public void setup(){
        filmeRealRepository = Mockito.mock(FilmeRealRepository.class);
        filmeConceitoRepository = Mockito.mock(FilmeConceitoRepository.class);
        filmeRealService = new FilmeRealService(filmeRealRepository,filmeConceitoRepository);
        FilmeConceito filmeConceito = new FilmeConceito();
        filmeConceito.setNome("Filme Conceito");
        filmeConceito.setDescricao("Filme Conceito");
        filmeConceito.setGenero("Testes");
        filmeConceito.setUuid(UUID.randomUUID());
        filmeReal = new FilmeReal();
        filmeReal.setFilmeConceito(filmeConceito);
        filmeConceito.setFilmesReais(List.of(filmeReal));
        filmeReal.setUuid(UUID.randomUUID());

    }
    @Test
    public void saveWhenFilmConceptNotFound_mustThrowException(){
        Assertions.assertThrows(
               FilmeConceitoNaoEncontradoException.class,() -> filmeRealService.save(UUID.randomUUID())
        );
    }
    @Test
    public void save_whenFilmeConceitoIsFound_mustCallRepositorySaveForCorrespondingFilmeReal() {
        Mockito.doReturn(Optional.of(filmeReal.getFilmeConceito())).when(filmeConceitoRepository).findByUuid(filmeReal.getFilmeConceito().getUuid());
        ArgumentCaptor<FilmeReal> captor = ArgumentCaptor.forClass(FilmeReal.class);
        filmeRealService.save(filmeReal.getFilmeConceito().getUuid());
        Mockito.verify(filmeRealRepository, Mockito.times(1)).save(captor.capture());
        FilmeReal found = captor.getValue();
        Assertions.assertEquals(filmeReal.getFilmeConceito(), found.getFilmeConceito());
        Assertions.assertNotNull(found.getUuid());
    }
    @Test
    public void save_whenFilmeConceitoIsFound_mustReturnCorrespondingFilmeReal() {
        Mockito.doReturn(Optional.of(filmeReal.getFilmeConceito())).when(filmeConceitoRepository).findByUuid(filmeReal.getFilmeConceito().getUuid());
        Mockito.doAnswer(invocationOnMock -> {
            FilmeReal receivedFilmeReal = invocationOnMock.getArgument(0);
            receivedFilmeReal.setId(1L);
            return receivedFilmeReal;
        }).when(filmeRealRepository).save(Mockito.any(FilmeReal.class));
        var response = filmeRealService.save(filmeReal.getFilmeConceito().getUuid());
        Assertions.assertEquals(filmeReal.getFilmeConceito(), response.getFilmeConceito());
        Assertions.assertEquals(1L, response.getId());
    }
    @Test
    public void listarFilmes_whenFilmeRealRelatedToFilmeConceitoNotFound_mustReturnEmptyList() {
        Mockito.doReturn(List.of()).when(filmeRealRepository).findByFilmeConceitoUuid(Mockito.any(UUID.class));
        var response = filmeRealService.listarFilmes(filmeReal.getFilmeConceito().getUuid());
        Assertions.assertEquals(List.of(), response);
    }

    @Test
    public void listarFilmes_whenFilmeRealRelatedToFilmeConceitoFound_mustReturnListOfIt() {
        Mockito.doReturn(List.of(filmeReal)).when(filmeRealRepository).findByFilmeConceitoUuid(filmeReal.getFilmeConceito().getUuid());
        var response = filmeRealService.listarFilmes(filmeReal.getFilmeConceito().getUuid());
        Assertions.assertInstanceOf(List.class, response);
        Assertions.assertEquals(1, response.size());
        Assertions.assertEquals(filmeReal.getFilmeConceito(), response.get(0).getFilmeConceito());
        Assertions.assertNotNull(response.getFirst().getUuid());
    }
    @Test
    public void listarFilmesAlugadosOuNao_whenAlugadosNotFound_mustReturnEmptyList() {
        Mockito.doReturn(List.of()).when(filmeRealRepository).findByUuidAndIsAlugado(Mockito.any(UUID.class), Mockito.eq(true));
        Assertions.assertEquals(List.of(), filmeRealService.listarFilmesAlugadosOuNao(UUID.randomUUID(), true));
    }
    @Test
    public void listarFilmesAlugadosOuNao_whenNaoAlugadosNotFound_mustReturnEmptyList() {
        Mockito.doReturn(List.of()).when(filmeRealRepository).findByUuidAndIsAlugado(Mockito.any(UUID.class), Mockito.eq(false));
        Assertions.assertEquals(List.of(), filmeRealService.listarFilmesAlugadosOuNao(UUID.randomUUID(), false));
    }
    @Test
    public void listarFilmesAlugadosOuNao_whenAlugadosFound_mustReturnListOfIt() {
        Mockito.doReturn(List.of(filmeReal)).when(filmeRealRepository).findByUuidAndIsAlugado(Mockito.any(UUID.class), Mockito.eq(true));
        Assertions.assertEquals(List.of(filmeReal), filmeRealService.listarFilmesAlugadosOuNao(UUID.randomUUID(), true));
    }
    @Test
    public void listarFilmesAlugadosOuNao_whenNaoAlugadosFound_mustReturnListOfIt() {
        Mockito.doReturn(List.of(filmeReal)).when(filmeRealRepository).findByUuidAndIsAlugado(Mockito.any(UUID.class), Mockito.eq(false));
        Assertions.assertEquals(List.of(filmeReal), filmeRealService.listarFilmesAlugadosOuNao(UUID.randomUUID(), false));
    }
    //Continuar isso depois!
//    @Test
//    public void listarFilmesRetornaOFilmeCorreto() {
//        UUID uuid = UUID.randomUUID();
//        filmeRealRepository.findByFilmeConceitoUuid(uuid);
//    }
//
}
