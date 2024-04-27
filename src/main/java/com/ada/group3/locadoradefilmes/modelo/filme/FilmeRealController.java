package com.ada.group3.locadoradefilmes.modelo.filme;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.mapping}/filmes-reais")
@RequiredArgsConstructor
public class FilmeRealController {

    private final FilmeRealService filmeRealService;

    @PostMapping
    public FilmeRealDto cadastrarFilme(@Valid @RequestBody FilmeRealRequest filmeRealRequest) {
        //TODO: adicionar retorno
        FilmeReal filmeReal = filmeRealService.save(filmeRealRequest.filmeConceitoId());
        FilmeConceito filmeConceito = filmeReal.getFilmeConceito();
        return new FilmeRealDto(
                filmeReal.getUuid(),
                new FilmeConceitoDto(filmeConceito.getUuid(), filmeConceito.getNome(), filmeConceito.getGenero(), filmeConceito.getDescricao()),
                filmeReal.isAlugado()
        );
    }

    @GetMapping
    public List<FilmeRealDto> listarFilmes() {
        return filmeRealService.listarFilmes().stream()
                .map(filme -> new FilmeRealDto(
                        filme.getUuid(),
                        new FilmeConceitoDto(
                                filme.getFilmeConceito().getUuid(),
                                filme.getFilmeConceito().getNome(),
                                filme.getFilmeConceito().getGenero(),
                                filme.getFilmeConceito().getDescricao()),
                        filme.isAlugado()
                )).toList();
    }



}
