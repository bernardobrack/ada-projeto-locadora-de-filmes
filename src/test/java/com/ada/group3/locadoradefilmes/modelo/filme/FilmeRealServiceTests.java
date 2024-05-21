package com.ada.group3.locadoradefilmes.modelo.filme;

import com.ada.group3.locadoradefilmes.exception.FilmeConceitoNaoEncontradoException;
import com.ada.group3.locadoradefilmes.exception.FilmeRealNaoEncontradoException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

public class FilmeRealServiceTests {


    private FilmeRealRepository filmeRealRepository;

    private FilmeConceitoRepository filmeConceitoRepository;


    private FilmeRealService filmeRealService;


    @BeforeEach
    public void setup(){
        filmeRealRepository = Mockito.mock(FilmeRealRepository.class);
        filmeConceitoRepository = Mockito.mock(FilmeConceitoRepository.class);
        filmeRealService = new FilmeRealService(filmeRealRepository,filmeConceitoRepository);
    }
    @Test
    public void saveWhenFilmConceptNotFound_mustThrowException(){
        Assertions.assertThrows(
               FilmeConceitoNaoEncontradoException.class,() -> filmeRealService.save(UUID.randomUUID())
        );
    }
    //Continuar isso depois!
//    @Test
//    public void listarFilmesRetornaOFilmeCorreto() {
//        UUID uuid = UUID.randomUUID();
//        filmeRealRepository.findByFilmeConceitoUuid(uuid);
//    }
//
}
