package com.ada.group3.locadoradefilmes.modelo.usuario;

import com.ada.group3.locadoradefilmes.exception.NaoEncontradoException;
import com.ada.group3.locadoradefilmes.exception.UsuarioNaoEncontradoException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Component
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;


    public List<UsuarioDto> listarTodos() {
        return this.usuarioRepository.findAll()
                .stream()
                .map(usuario -> modelMapper.map(usuario, UsuarioDto.class))
                .toList();
    }

    public UsuarioDto buscarPorUsername(String username) {
        return this.usuarioRepository.findByUsername(username)
                .map(usuario -> modelMapper.map(usuario, UsuarioDto.class))
                .orElseThrow(UsuarioNaoEncontradoException::new);
    }
    public Usuario getByUsernameEntity(String username){
        return this.usuarioRepository.findByUsername(username)
                .orElseThrow(UsuarioNaoEncontradoException::new);
    }

    public UsuarioDto adicionarUsuario(UsuarioRequest usuarioRequest) {
        Usuario usuario = modelMapper.map(usuarioRequest, Usuario.class);
        usuario.setActive(true);
        usuario.setIsLate(false);
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        usuario.setRole(Usuario.Role.CLIENTE);
        Usuario savedUsuario = this.usuarioRepository.save(usuario);
        return modelMapper.map(savedUsuario, UsuarioDto.class);
    }
    public void atualizar(String username,UsuarioUpdateRequest request){
        Usuario usuarioFound = usuarioRepository.findByUsername(username).orElseThrow(UsuarioNaoEncontradoException::new);
        usuarioFound.setUsername(request.getUsername());
        usuarioFound.setPassword(passwordEncoder.encode(request.getPassword()));
        usuarioRepository.save(usuarioFound);

    }
    @Transactional
    public void marcarAtraso(String username){
            this.usuarioRepository.marcarAtraso(username);
    }
    @Transactional
    public void desmarcarAtraso(String username){
        this.usuarioRepository.desmarcarAtraso(username);
    }
    public void desativarUsuario(String username){
       Usuario usuario = this.usuarioRepository.findByUsername(username).orElseThrow(UsuarioNaoEncontradoException::new);
       usuario.setActive(false);
       this.usuarioRepository.save(usuario);
    }



}
