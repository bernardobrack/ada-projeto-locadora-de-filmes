package com.ada.group3.locadoradefilmes.modelo.filme;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import jakarta.transaction.Transactional;
import java.util.List;

@RestController
@RequestMapping("${api.mapping}/filmes-conceito")
@RequiredArgsConstructor
public class FilmeConceitoController {

    private final FilmeConceitoService service;

    @GetMapping
    public List<FilmeConceitoDto> listarTodos() {
        return this.service.listarTodos();
    }

    @GetMapping(value = "busca", params = "nome")
    public FilmeConceitoDto buscarPorNome(@RequestParam String nome) {
        return this.service.buscarPorNome(nome);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FilmeConceitoDto adicionarFilmeConceito(@RequestBody FilmeConceitoRequest filmeConceitoRequest) {
        return this.service.adicionarFilmeConceito(filmeConceitoRequest);
    }

    @PutMapping("/{nome}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void atualizar(@PathVariable String nome, @RequestBody FilmeConceitoDto filmeConceitoAtualizado) {
        this.service.atualizar(nome, filmeConceitoAtualizado);
    }

    @Transactional
    @DeleteMapping("/{nome}")
    public void excluir(@PathVariable String nome) {
        this.service.excluir(nome);
    }
}
