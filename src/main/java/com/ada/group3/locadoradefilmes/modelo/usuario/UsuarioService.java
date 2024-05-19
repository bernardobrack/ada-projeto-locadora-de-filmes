package com.ada.group3.locadoradefilmes.modelo.usuario;

import com.ada.group3.locadoradefilmes.exception.*;
import com.ada.group3.locadoradefilmes.modelo.usuario.EmailValidation.EmailValidationResponse;
import com.ada.group3.locadoradefilmes.modelo.usuario.EmailValidation.EmailValidationService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
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
    private final EmailValidationService emailValidationService;

    @Value("${mailboxlayer.api.key}")
    private String apiKey;

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

    public Usuario getByUsernameEntity(String username) {
        return this.usuarioRepository.findByUsername(username)
                .orElseThrow(UsuarioNaoEncontradoException::new);
    }

    public UsuarioDto adicionarUsuario(UsuarioRequest usuarioRequest) {
        String email = usuarioRequest.getEmail();
        var response = emailValidationService.validarEmail(email, apiKey);
        if (!response.isFormat_valid() || !response.isMx_found()) {
            throw new EmailInvalidoException("E-mail inválido");
        }
        if(usuarioRepository.findByUsername(usuarioRequest.getUsername()).isPresent()){
            throw new UsuarioJaExisteException();
        }

        Usuario usuario = modelMapper.map(usuarioRequest, Usuario.class);
        usuario.setActive(true);
        usuario.setIsLate(false);
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        usuario.setRole(Usuario.Role.CLIENTE);
        Usuario savedUsuario = this.usuarioRepository.save(usuario);
        return modelMapper.map(savedUsuario, UsuarioDto.class);
    }

    public void atualizar(String username, UsuarioUpdateRequest request) {
        Usuario usuarioFound = usuarioRepository.findByUsername(username).orElseThrow(UsuarioNaoEncontradoException::new);
        if(usuarioRepository.findByUsername(request.getUsername()).isPresent()){
            throw new UsuarioJaExisteException();
        }
        usuarioFound.setUsername(request.getUsername());
        usuarioFound.setPassword(passwordEncoder.encode(request.getPassword()));
        usuarioRepository.save(usuarioFound);
    }

    @Transactional
    public void marcarAtraso(String username) {
        this.usuarioRepository.marcarAtraso(username);
    }

    @Transactional
    public void desmarcarAtraso(String username) {
        this.usuarioRepository.desmarcarAtraso(username);
    }

    public void desativarUsuario(String username) {
        Usuario usuario = this.usuarioRepository.findByUsername(username).orElseThrow(UsuarioNaoEncontradoException::new);
        usuario.setActive(false);
        this.usuarioRepository.save(usuario);
    }


}
