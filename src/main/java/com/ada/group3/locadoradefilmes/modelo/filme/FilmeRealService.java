package com.ada.group3.locadoradefilmes.modelo.filme;

import com.ada.group3.locadoradefilmes.exception.FilmeConceitoNaoEncontradoException;
import com.ada.group3.locadoradefilmes.exception.NaoEncontradoException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class FilmeRealService {

    private final FilmeRealRepository filmeRealRepository;
    private final FilmeConceitoRepository filmeConceitoRepository;

    public FilmeReal save(UUID filmeConceitoId) {
        FilmeConceito filmeConceito = filmeConceitoRepository.findByUuid(filmeConceitoId).orElseThrow(FilmeConceitoNaoEncontradoException::new);
        FilmeReal filmeReal = new FilmeReal();
        filmeReal.setFilmeConceito(filmeConceito);
        filmeReal.setUuid(UUID.randomUUID());
        return filmeRealRepository.save(filmeReal);
    }

    public List<FilmeReal> listarFilmes(UUID filmeConceitoId) {
        return filmeRealRepository.findByFilmeConceitoUuid(filmeConceitoId);
    }

    public List<FilmeReal> listarFilmesAlugadosOuNao(UUID filmeConceitoId, Boolean alugado) {
        return filmeRealRepository.findByUuidAndIsAlugado(filmeConceitoId,alugado);
    }

}
