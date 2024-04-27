package com.ada.group3.locadoradefilmes.modelo.usuario;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("${api.mapping}/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService service;

    @GetMapping
    public List<UsuarioDto> listarTodos() {
        return this.service.listarTodos();
    }

    @GetMapping(value = "busca", params = "login")
    public UsuarioDto buscarPorLogin(@RequestParam String login) {
        return this.service.buscarPorLogin(login);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioDto adicionarUsuario(@RequestBody UsuarioRequest usuarioRequest) {
        return this.service.adicionarUsuario(usuarioRequest);
    }

    @PutMapping("/{login}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void atualizar(@PathVariable String login, @RequestBody UsuarioDto usuarioAtualizado) {
        this.service.atualizar(login, usuarioAtualizado);
    }

    @Transactional
    @PatchMapping("/{login}/atraso/marcar")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void marcarAtraso(@PathVariable String login) {
        this.service.marcarAtraso(login);
    }

    @Transactional
    @PatchMapping("/{login}/atraso/desmarcar")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desmarcarAtraso(@PathVariable String login) {
        this.service.desmarcarAtraso(login);
    }


    @DeleteMapping("/{login}")
    public void desativarUsuario(@PathVariable String login) {
        this.service.desativarUsuario(login);
    }

}






