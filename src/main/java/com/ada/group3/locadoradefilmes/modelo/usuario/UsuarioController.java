package com.ada.group3.locadoradefilmes.modelo.usuario;


import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;
import static com.ada.group3.locadoradefilmes.security.PermissionValidation.validatePermission;

@RestController
@RequestMapping("${api.mapping}/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService service;

    @GetMapping
    @PreAuthorize("hasRole(T(com.ada.group3.locadoradefilmes.modelo.usuario.Usuario.Role).ADMIN.name())")
    public List<UsuarioDto> listarTodos() {
        return this.service.listarTodos();
    }

    @GetMapping("/{username}")
    public UsuarioDto buscarPorLogin(@PathVariable String username, Authentication authentication) {
            if(validatePermission.apply(authentication,username)){
                    throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Acesso negado");
            }
        return this.service.buscarPorUsername(username);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioDto adicionarUsuario(@Valid @RequestBody UsuarioRequest usuarioRequest) {
        return this.service.adicionarUsuario(usuarioRequest);
    }

    @PutMapping("/{username}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void atualizar(@PathVariable String username, @RequestBody UsuarioDto usuarioAtualizado) {
        this.service.atualizar(username, usuarioAtualizado);
    }

    @Transactional
    @PatchMapping("/{username}/atraso/marcar")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void marcarAtraso(@PathVariable String username) {
        this.service.marcarAtraso(username);
    }

    @Transactional
    @PatchMapping("/{username}/atraso/desmarcar")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desmarcarAtraso(@PathVariable String username) {
        this.service.desmarcarAtraso(username);
    }


    @DeleteMapping("/{username}")
    public void desativarUsuario(@PathVariable String username,Authentication authentication) {
        if(validatePermission.apply(authentication,username)){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Acesso negado");
        }
        this.service.desativarUsuario(username);
    }

}






