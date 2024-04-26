package com.ada.group3.locadoradefilmes.modelo.filme;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("${api.mapping}/filmes-conceito")
@RequiredArgsConstructor
public class FilmeConceitoController {

    private final FilmeConceitoService service;

    @GetMapping
    public List<FilmeConceitoDto> listarTodos() {
        return this.service.listarTodos();
    }

    @GetMapping(value = "busca", params = "uuid")
    public FilmeConceitoDto buscarPorUUID(@RequestParam UUID uuid) {
        return this.service.buscarPorUUID(uuid);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FilmeConceitoDto adicionarFilmeConceito(@RequestBody FilmeConceitoRequest filmeConceitoRequest) {
        return this.service.adicionarFilmeConceito(filmeConceitoRequest);
    }

    @PutMapping("/{uuid}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void atualizar(@PathVariable UUID uuid, @RequestBody FilmeConceitoDto filmeConceitoAtualizado) {
        this.service.atualizar(uuid, filmeConceitoAtualizado);
    }

    @Transactional
    @DeleteMapping("/{uuid}")
    public void excluir(@PathVariable UUID uuid) {
        this.service.excluir(uuid);
    }
}
