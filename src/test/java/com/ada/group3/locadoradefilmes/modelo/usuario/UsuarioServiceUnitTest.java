package com.ada.group3.locadoradefilmes.modelo.usuario;

import com.ada.group3.locadoradefilmes.exception.UsuarioJaExisteException;
import com.ada.group3.locadoradefilmes.exception.UsuarioNaoEncontradoException;
import com.ada.group3.locadoradefilmes.modelo.usuario.EmailValidation.EmailValidationResponse;
import com.ada.group3.locadoradefilmes.modelo.usuario.EmailValidation.EmailValidationService;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import org.mockito.Mockito;
import org.modelmapper.ModelMapper;

import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

public class UsuarioServiceUnitTest {

    private UsuarioRepository usuarioRepository;
    private ModelMapper modelMapper;
    private PasswordEncoder passwordEncoder;
    private EmailValidationService emailValidationService;

    private UsuarioService usuarioService;


    @BeforeEach
    public void setup() {
        usuarioRepository = Mockito.mock(UsuarioRepository.class);
        modelMapper = Mockito.mock(ModelMapper.class);
        passwordEncoder = Mockito.mock(PasswordEncoder.class);
        emailValidationService = Mockito.mock(EmailValidationService.class);
        usuarioService = new UsuarioService
                (usuarioRepository, modelMapper,
                        passwordEncoder, emailValidationService);
        Usuario usuario = new Usuario();
        usuario.setName("Pedro");
        usuario.setLastName("santos");
        usuario.setEmail("pedrosantos@gmail.com");
        usuario.setCpf("86606576083");
        usuario.setUsername("pedro.santos");
        usuario.setPassword("St@rG@z3r^#");

        Usuario usuario2 = new Usuario();
        usuario2.setName("Jose");
        usuario2.setLastName("neto");
        usuario2.setEmail("joseneto@gmail.com");
        usuario2.setCpf("62459846029");
        usuario2.setUsername("jose.neto");
        usuario2.setPassword("St@rG@z3r^#");


        EmailValidationResponse simulatedResponse = new EmailValidationResponse();
        simulatedResponse.setFormat_valid(true);
        simulatedResponse.setMx_found(true);

        when(emailValidationService.validarEmail(anyString(), any())).thenReturn(simulatedResponse);

        Mockito.when(usuarioRepository.findByUsername("pedro.santos")).thenReturn(Optional.of(usuario));
        Mockito.when(usuarioRepository.findByUsername("jose.neto")).thenReturn(Optional.of(usuario2));
        Mockito.when(usuarioRepository.findAll()).thenReturn(Arrays.asList(usuario, usuario2));
        Mockito.when(modelMapper.map(any(Usuario.class), eq(UsuarioDto.class)))
                .thenAnswer(invocation -> {
                    Usuario u = invocation.getArgument(0);
                    UsuarioDto dto = new UsuarioDto();
                    dto.setName(u.getName());
                    dto.setLastName(u.getLastName());
                    dto.setEmail(u.getEmail());
                    dto.setCpf(u.getCpf());
                    dto.setUsername(u.getUsername());
                    dto.setPassword(u.getPassword());
                    return dto;
                });
        Mockito.when(modelMapper.map(any(UsuarioRequest.class), eq(Usuario.class)))
                .thenAnswer(invocation -> {
                    UsuarioRequest u = invocation.getArgument(0);
                    Usuario usuario3 = new Usuario();
                    usuario3.setName(u.getName());
                    usuario3.setLastName(u.getLastName());
                    usuario3.setEmail(u.getEmail());
                    usuario3.setCpf(u.getCpf());
                    usuario3.setUsername(u.getUsername());
                    usuario3.setPassword(u.getPassword());
                    return usuario3;
                });


    }

    @Test
    public void listarTodos_deveRetornarListaDeUsuarios() {

        List<UsuarioDto> usuarios = usuarioService.listarTodos();
        Assertions.assertEquals(2, usuarios.size());
        Assertions.assertEquals("Pedro", usuarios.get(0).getName());
        Assertions.assertEquals("Jose", usuarios.get(1).getName());


    }

    @Test
    public void buscarPorUsername_deveRetornarUsuario() {
        UsuarioDto usuario = usuarioService.buscarPorUsername("pedro.santos");
        Assertions.assertEquals("pedro.santos", usuario.getUsername());


    }

    @Test
    public void buscarPorUsernameNaoExistente_deveRetornarException() {
        Assertions.assertThrows(UsuarioNaoEncontradoException.class,
                () -> usuarioService.buscarPorUsername("marcelo.pereira"));
    }

    @Test
    public void criarUsuario_deveTerSucesso() {
        UsuarioRequest usuarioRequest = new UsuarioRequest();
        usuarioRequest.setName("wagner");
        usuarioRequest.setLastName("souza");
        usuarioRequest.setCpf("88730477000");
        usuarioRequest.setEmail("wagnersouza@gmail.com");
        usuarioRequest.setUsername("wagner.souza");
        usuarioRequest.setPassword("St@rG@z3r^#");

        usuarioService.adicionarUsuario(usuarioRequest);

        Mockito.verify(usuarioRepository, Mockito.times(1)).save(any());
    }

    @Test
    public void criarUsuarioJaExistente_deveRetornarException() {
        UsuarioRequest usuarioRequest = new UsuarioRequest();
        usuarioRequest.setName("wagner");
        usuarioRequest.setLastName("souza");
        usuarioRequest.setCpf("88730477000");
        usuarioRequest.setEmail("wagnersouza@gmail.com");
        usuarioRequest.setUsername("pedro.santos");
        usuarioRequest.setPassword("St@rG@z3r^#");

        Assertions.assertThrows(UsuarioJaExisteException.class,
                () -> usuarioService.adicionarUsuario(usuarioRequest));

    }

    @Test
    public void atualizarUsuarioJaExistente_deveTerSucesso() {
        Optional<Usuario> usuario = usuarioRepository.findByUsername("pedro.santos");
        UsuarioUpdateRequest updateRequest = new UsuarioUpdateRequest();
        updateRequest.setUsername("pedro.maia");
        updateRequest.setPassword("St@rG@z3r^#");
        usuarioService.atualizar("pedro.santos", updateRequest);
        Assertions.assertTrue(usuario.isPresent());
        Assertions.assertEquals("pedro.maia", usuario.get().getUsername());
    }

    @Test
    public void atualizarUsuarioNaoExistente_deveRetornarException() {
        UsuarioUpdateRequest updateRequest = new UsuarioUpdateRequest();
        Assertions.assertThrows(UsuarioNaoEncontradoException.class,
                () -> usuarioService.atualizar("marcos.silva", updateRequest));
    }

    @Test
    public void atualizarUsernameDeUmUsuarioParaUmQueJaExiste_deveRetornarException() {
        UsuarioUpdateRequest updateRequest = new UsuarioUpdateRequest();
        updateRequest.setUsername("pedro.santos");
        Assertions.assertThrows(UsuarioJaExisteException.class,
                () -> usuarioService.atualizar("jose.neto", updateRequest));
    }

    @Test
    public void marcarAtraso_deveTerSucesso() {
        usuarioRepository.marcarAtraso("jose.neto");
        Mockito.verify(usuarioRepository, Mockito.times(1)).marcarAtraso("jose.neto");
    }

    @Test
    public void desmarcarAtraso_deveTerSucesso() {
        usuarioRepository.desmarcarAtraso("jose.neto");
        Mockito.verify(usuarioRepository, Mockito.times(1)).desmarcarAtraso("jose.neto");

    }

    @Test
    public void desativarUsuario_deveTerSucesso() {
        Optional<Usuario> usuario = usuarioRepository.findByUsername("jose.neto");
        Assertions.assertTrue(usuario.isPresent());
        usuarioService.desativarUsuario(usuario.get().getUsername());
        Assertions.assertFalse(usuario.get().getActive());
    }

    @Test
    public void desativarUsuarioNaoExistente_deveRetornarException() {
        Assertions.assertThrows(UsuarioNaoEncontradoException.class,
                () -> usuarioService.desativarUsuario("ricardo.silva"));
    }


}











