package com.ada.group3.locadoradefilmes.login;


import com.ada.group3.locadoradefilmes.modelo.usuario.Usuario;
import com.ada.group3.locadoradefilmes.modelo.usuario.UsuarioService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AuthServiceIntegrationTest {

    private UsuarioService usuarioService;


    private AuthService authService;

    @BeforeEach
   public void setUp() {
        usuarioService = Mockito.mock(UsuarioService.class);
        authService = new AuthService(usuarioService);
    }

    @Test
    public void loadUserByUsername_UserExists_ReturnsUserDetails() {
        String login = "usuarioTest";
        Usuario mockUser = new Usuario();
        mockUser.setUsername(login);
        when(usuarioService.getByUsernameEntity(login)).thenReturn(mockUser);


        UserDetails userDetails = authService.loadUserByUsername(login);

        assertNotNull(userDetails);
        assertEquals(login, userDetails.getUsername());
        verify(usuarioService, times(1)).getByUsernameEntity(login);
    }

    @Test
    public void loadUserByUsername_UserDoesNotExist_ThrowsUsernameNotFoundException() {

        String login = "usuarioNaoExistente";
        when(usuarioService.getByUsernameEntity(login)).thenThrow(new UsernameNotFoundException("Usuario Nao Encontrado"));

        assertThrows(UsernameNotFoundException.class, () -> authService.loadUserByUsername(login));
        verify(usuarioService, times(1)).getByUsernameEntity(login);
    }
}
