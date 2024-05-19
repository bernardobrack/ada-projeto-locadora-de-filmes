package com.ada.group3.locadoradefilmes.modelo.usuario;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UsuarioDtoUnitTest {
    @Test
    public void testEquals() {
        UsuarioDto user1 = new UsuarioDto("John", "Doe", "12345678901", "john.doe@example.com", "johndoe", "password", Usuario.Role.CLIENTE, true, false);
        UsuarioDto user2 = new UsuarioDto("John", "Doe", "12345678901", "john.doe@example.com", "johndoe", "password", Usuario.Role.CLIENTE, true, false);
        assertEquals(user1, user2);
    }

    @Test
    public void testNotEquals() {
        UsuarioDto user1 = new UsuarioDto("John", "Doe", "12345678901", "john.doe@example.com", "johndoe", "password", Usuario.Role.CLIENTE, true, false);
        UsuarioDto user2 = new UsuarioDto("Jane", "Doe", "12345678901", "john.doe@example.com", "johndoe", "password", Usuario.Role.CLIENTE, true, false);
        assertNotEquals(user1, user2);
    }

    @Test
    public void testHashCode() {
        UsuarioDto user1 = new UsuarioDto("John", "Doe", "12345678901", "john.doe@example.com", "johndoe", "password", Usuario.Role.CLIENTE, true, false);
        UsuarioDto user2 = new UsuarioDto("John", "Doe", "12345678901", "john.doe@example.com", "johndoe", "password", Usuario.Role.CLIENTE, true, false);
        assertEquals(user1.hashCode(), user2.hashCode());
    }

    @Test
    public void testToString() {
        UsuarioDto user = new UsuarioDto("John", "Doe", "12345678901", "john.doe@example.com", "johndoe", "password", Usuario.Role.CLIENTE, true, false);
        String expected = "UsuarioDto(name=John, lastName=Doe, cpf=12345678901, email=john.doe@example.com, username=johndoe, password=password, role=CLIENTE, active=true, isLate=false)";
        assertEquals(expected, user.toString());
    }

    @Test
    public void testCanEqual() {
        UsuarioDto user1 = new UsuarioDto();
        UsuarioDto user2 = new UsuarioDto();
        assertTrue(user1.canEqual(user2));
    }

    @Test
    public void testConstructorWithAllArgs() {
        UsuarioDto user = new UsuarioDto("John", "Doe", "12345678901", "john.doe@example.com", "johndoe", "password", Usuario.Role.CLIENTE, true, false);
        assertNotNull(user);
        assertEquals("John", user.getName());
        assertEquals("Doe", user.getLastName());
        assertEquals("12345678901", user.getCpf());
        assertEquals("john.doe@example.com", user.getEmail());
        assertEquals("johndoe", user.getUsername());
        assertEquals("password", user.getPassword());
        assertEquals(Usuario.Role.CLIENTE, user.getRole());
        assertTrue(user.getActive());
        assertFalse(user.getIsLate());
    }
}

