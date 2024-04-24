package com.ada.group3.locadoradefilmes.modelo.usuario;

import com.ada.group3.locadoradefilmes.exception.NaoEncontradoException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Component
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final ModelMapper modelMapper;


    public List<UsuarioDto> listarTodos() {
        return this.usuarioRepository.findAll()
                .stream()
                .map(usuario -> modelMapper.map(usuario, UsuarioDto.class))
                .toList();
    }

    public UsuarioDto buscarPorLogin(String login) {
        return this.usuarioRepository.findByLogin(login)
                .map(usuario -> modelMapper.map(usuario, UsuarioDto.class))
                .orElseThrow(NaoEncontradoException::new);
    }

    public UsuarioDto adicionarUsuario(UsuarioDto usuarioDto) {
        Usuario usuario = modelMapper.map(usuarioDto, Usuario.class);
        Usuario savedUsuario = this.usuarioRepository.save(usuario);
        return modelMapper.map(savedUsuario, UsuarioDto.class);
    }
    public void atualizar(String login, UsuarioDto usuarioAtualizado){
        Usuario usuarioFound = this.usuarioRepository.findByLogin(login).orElseThrow(NaoEncontradoException::new);
        Usuario usuario = this.modelMapper.map(usuarioAtualizado,Usuario.class);
        usuario.setId(usuarioFound.getId());
        this.usuarioRepository.save(usuario);

    }
    @Transactional
    public void excluir(String login) {
        this.usuarioRepository.deleteByLogin(login);
    }


}
