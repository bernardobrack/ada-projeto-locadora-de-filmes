package com.ada.group3.locadoradefilmes.modelo.usuario;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class UsuarioRequestUnitTest {

    @Test
    public void testToString(){
        UsuarioRequest usuarioRequest = new UsuarioRequest();
        usuarioRequest.setName("John");
        usuarioRequest.setLastName("Doe");
        usuarioRequest.setCpf("123.456.789-00");
        usuarioRequest.setEmail("john.doe@example.com");
        usuarioRequest.setUsername("john_doe");
        usuarioRequest.setPassword("Password123!");

        String toStringResult = usuarioRequest.toString();

        assertTrue(toStringResult.contains("name=John"));
        assertTrue(toStringResult.contains("lastName=Doe"));
        assertTrue(toStringResult.contains("cpf=123.456.789-00"));
        assertTrue(toStringResult.contains("email=john.doe@example.com"));
        assertTrue(toStringResult.contains("username=john_doe"));
        assertTrue(toStringResult.contains("password=Password123!"));
    }
}
