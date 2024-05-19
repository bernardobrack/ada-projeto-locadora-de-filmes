package com.ada.group3.locadoradefilmes.modelo.filme;

import com.ada.group3.locadoradefilmes.modelo.usuario.Usuario;
import com.ada.group3.locadoradefilmes.modelo.usuario.UsuarioDto;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class FilmeRealDTOtests {

    @Test

    public void construtorDoDTOcomFilmeConceitoNuloNAOdeveFuncionar(){
        FilmeConceitoDto filmeConceitoDto = null;
        new FilmeRealDto(UUID.randomUUID(), filmeConceitoDto, true);
    }


    @Test
    public void testConstructorWithAllArgs() {
        UUID uuid = UUID.randomUUID();
        FilmeConceitoDto filmeConceitoDto = new FilmeConceitoDto(1L, "filme", "Descrição");
        FilmeRealDto filme = new FilmeRealDto(uuid, filmeConceitoDto, true);
        assertNotNull(filme);
        assertEquals(uuid, filme.id());
    }

    @Test
    public void testandoOConstrutorDeFilmeRealDTO() {
        // Cenário
        FilmeConceitoDto filmeConceitoDto = new FilmeConceitoDto(1, "Titulo", "Genero");

        // Ação
        FilmeRealDto filmeRealDto = new FilmeRealDto(UUID.randomUUID(), filmeConceitoDto, false);

        // Verificação
        assertEquals(filmeRealDto.id(), filmeRealDto.id());
        assertEquals(filmeRealDto.filme(), filmeConceitoDto);
        assertEquals(filmeRealDto.isAlugado(), false);
    }


    @Test
    public void testandoOMetodoEqualsDoFilmeRealDTO() {
        // Cenário
        UUID id = UUID.randomUUID();
        FilmeConceitoDto filmeConceitoDto = new FilmeConceitoDto(1, "Titulo", "Genero");
        FilmeRealDto filmeRealDto1 = new FilmeRealDto(id, filmeConceitoDto, false);
        FilmeRealDto filmeRealDto2 = new FilmeRealDto(id, filmeConceitoDto, false);

        // Teste
        assertEquals(filmeRealDto1, filmeRealDto2);
        assertTrue(filmeRealDto1.equals(filmeRealDto2));
    }

}
