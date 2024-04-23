package com.ada.group3.locadoradefilmes.modelo.usuario;

import com.ada.group3.locadoradefilmes.exception.NaoEncontradoException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Component
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;


    public List<Usuario> listarTodos() {
        return this.usuarioRepository
                .findAll()
                .stream()
                .toList();

    }
    public Usuario buscarPorLogin(String login){
        return this.usuarioRepository.findByLogin(login).orElseThrow(NaoEncontradoException::new);
    }

    public Usuario adicionarUsuario(Usuario usuario){
        return this.usuarioRepository.save(usuario);
    }
    @Transactional
    public void excluir(String login){
        this.usuarioRepository.deleteByLogin(login);
    }


}
