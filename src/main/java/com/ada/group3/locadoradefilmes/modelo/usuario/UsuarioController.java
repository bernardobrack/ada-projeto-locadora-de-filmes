package com.ada.group3.locadoradefilmes.modelo.usuario;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.mapping}/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService service;

    @GetMapping
    public List<Usuario> listarTodos() {
        return this.service.listarTodos();
    }

    @GetMapping("/{login}")
    public Usuario buscarPorLogin(@PathVariable String login) {
        return this.service.buscarPorLogin(login);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Usuario adicionarUsuario(@RequestBody Usuario usuario) {
        return this.service.adicionarUsuario(usuario);
    }

    @Transactional
    @DeleteMapping("/{login}")
    public void excluir(@PathVariable String login) {
        this.service.excluir(login);
    }

}






