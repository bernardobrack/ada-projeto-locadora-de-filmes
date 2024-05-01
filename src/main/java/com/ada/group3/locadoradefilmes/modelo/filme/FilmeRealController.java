package com.ada.group3.locadoradefilmes.modelo.filme;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("${api.mapping}/filmes-conceito/{filmeConceitoId}/filmes-reais")
@RequiredArgsConstructor
public class FilmeRealController {

    private final FilmeRealService filmeRealService;

    @PostMapping
    @PreAuthorize("hasRole(T(com.ada.group3.locadoradefilmes.modelo.usuario.Usuario.Role).ADMIN.name())")
    public FilmeRealDto cadastrarFilme(@Valid @RequestBody FilmeRealRequest filmeRealRequest, @PathVariable UUID filmeConceitoId) {
        FilmeReal filmeReal = filmeRealService.save(filmeConceitoId);
        FilmeConceito filmeConceito = filmeReal.getFilmeConceito();
        return new FilmeRealDto(
                filmeReal.getUuid(),
                new FilmeConceitoDto(filmeConceito.getUuid(), filmeConceito.getNome(), filmeConceito.getGenero(), filmeConceito.getDescricao()),
                filmeReal.isAlugado()
        );
    }

    @GetMapping
    public List<FilmeRealDto> listarFilmes(@PathVariable UUID filmeConceitoId) {
        return filmeRealService.listarFilmes(filmeConceitoId).stream()
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

    @GetMapping(params = "alugado")
    public List<FilmeRealDto> listarFilmesAlugados(@PathVariable UUID filmeConceitoId, @RequestParam Boolean alugado) {
        return this.filmeRealService.listarFilmesAlugadosOuNao(filmeConceitoId, alugado).stream()
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
