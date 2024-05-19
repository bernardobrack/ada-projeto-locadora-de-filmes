package com.ada.group3.locadoradefilmes.modelo.usuario;

import com.ada.group3.locadoradefilmes.modelo.aluguel.Aluguel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UsuarioUnitTest {
    private Usuario usuario;

    @BeforeEach
    void setUp() {
        usuario = new Usuario();
        usuario.setRole(Usuario.Role.CLIENTE);
        usuario.setAccountExpired(false);
        usuario.setAccountLocked(false);
        usuario.setCredentialsExpired(false);
        usuario.setActive(true);
    }

    @Test
    void testGetAuthorities() {
        Collection<? extends GrantedAuthority> authorities = usuario.getAuthorities();
        assertNotNull(authorities);
        assertEquals(1, authorities.size());
        assertTrue(authorities.contains(new SimpleGrantedAuthority("ROLE_CLIENTE")));
    }

    @Test
    void testIsAccountNonExpired() {
        assertTrue(usuario.isAccountNonExpired());

        usuario.setAccountExpired(true);
        assertFalse(usuario.isAccountNonExpired());
    }

    @Test
    void testIsAccountNonLocked() {
        assertTrue(usuario.isAccountNonLocked());

        usuario.setAccountLocked(true);
        assertFalse(usuario.isAccountNonLocked());
    }

    @Test
    void testIsCredentialsNonExpired() {
        assertTrue(usuario.isCredentialsNonExpired());

        usuario.setCredentialsExpired(true);
        assertFalse(usuario.isCredentialsNonExpired());
    }

    @Test
    void testIsEnabled() {
        assertTrue(usuario.isEnabled());

        usuario.setActive(false);
        assertFalse(usuario.isEnabled());
    }

    @Test
    void testSetId() {
        usuario.setId(1L);
        assertEquals(1L, usuario.getId());
    }

    @Test
    void testSetAccountExpired() {
        usuario.setAccountExpired(true);
        assertTrue(usuario.isAccountExpired());
    }

    @Test
    void testSetAccountLocked() {
        usuario.setAccountLocked(true);
        assertTrue(usuario.isAccountLocked());
    }

    @Test
    void testSetCredentialsExpired() {
        usuario.setCredentialsExpired(true);
        assertTrue(usuario.isCredentialsExpired());
    }

    @Test
    void testSetAlugueis() {
        List<Aluguel> alugueis = new ArrayList<>();
        usuario.setAlugueis(alugueis);
        assertEquals(alugueis, usuario.getAlugueis());
    }

    @Test
    void testGetId() {
        usuario.setId(1L);
        assertEquals(1L, usuario.getId());
    }

    @Test
    void testIsAccountExpired() {
        usuario.setAccountExpired(true);
        assertTrue(usuario.isAccountExpired());
    }

    @Test
    void testIsAccountLocked() {
        usuario.setAccountLocked(true);
        assertTrue(usuario.isAccountLocked());
    }

    @Test
    void testIsCredentialsExpired() {
        usuario.setCredentialsExpired(true);
        assertTrue(usuario.isCredentialsExpired());
    }

    @Test
    void testGetAlugueis() {
        List<Aluguel> alugueis = new ArrayList<>();
        usuario.setAlugueis(alugueis);
        assertEquals(alugueis, usuario.getAlugueis());
    }

    @Test
    void testSetName() {
        String name = "John";
        usuario.setName(name);
        assertEquals(name, usuario.getName());
    }
    @Test
    void testAllArgsConstructor() {
        Long id = 1L;
        String name = "John";
        String lastName = "Doe";
        String cpf = "123.456.789-00";
        String email = "john.doe@example.com";
        String username = "johndoe";
        String password = "password";
        Usuario.Role role = Usuario.Role.CLIENTE;
        Boolean active = true;
        Boolean isLate = false;
        boolean accountExpired = false;
        boolean accountLocked = false;
        boolean credentialsExpired = false;
        List<Aluguel> alugueis = new ArrayList<>();

        Usuario usuario = new Usuario(
                id, name, lastName, cpf, email, username, password,
                role, active, isLate, accountExpired, accountLocked,
                credentialsExpired, alugueis
        );

        assertEquals(id, usuario.getId());
        assertEquals(name, usuario.getName());
        assertEquals(lastName, usuario.getLastName());
        assertEquals(cpf, usuario.getCpf());
        assertEquals(email, usuario.getEmail());
        assertEquals(username, usuario.getUsername());
        assertEquals(password, usuario.getPassword());
        assertEquals(role, usuario.getRole());
        assertEquals(active, usuario.isEnabled());
        assertEquals(isLate, usuario.getIsLate());
        assertEquals(accountExpired, usuario.isAccountExpired());
        assertEquals(accountLocked, usuario.isAccountLocked());
        assertEquals(credentialsExpired, usuario.isCredentialsExpired());
        assertEquals(alugueis, usuario.getAlugueis());
    }
}


