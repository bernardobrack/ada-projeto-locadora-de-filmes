package com.ada.group3.locadoradefilmes.modelo.usuario;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UsuarioUpdateRequestUnitTest {

    @Test
    public void toStringTest(){
        UsuarioUpdateRequest request = new UsuarioUpdateRequest();

        request.setUsername("kauan.silva");
        request.setPassword("password");

        String requestToString = request.toString();

        assertTrue(requestToString.contains("username=kauan.silva"));
        assertTrue(requestToString.contains("password=password"));
    }
    @Test
    public void allArgsConstructorTest(){
        String usuarioEsperado = "kauan.silva";
        String senhaEsperada = "password";

        UsuarioUpdateRequest request = new UsuarioUpdateRequest(usuarioEsperado,senhaEsperada);

        assertNotNull(request);
        assertEquals(usuarioEsperado,request.getUsername());
        assertEquals(senhaEsperada,request.getPassword());
    }


}
