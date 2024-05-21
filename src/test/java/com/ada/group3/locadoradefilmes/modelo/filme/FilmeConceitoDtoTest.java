package com.ada.group3.locadoradefilmes.modelo.filme;

import org.junit.jupiter.api.Test;

import java.util.Objects;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FilmeConceitoDtoTest {

    @Test
    void testConstrutorComUUID() {
        UUID uuid = UUID.randomUUID();
        String nome = "Filme Teste";
        String genero = "Ação";
        String descricao = "Descrição do filme teste";

        FilmeConceitoDto filme = new FilmeConceitoDto(uuid, nome, genero, descricao);

        assertEquals(uuid, filme.getUuid());
        assertEquals(nome, filme.getNome());
        assertEquals(genero, filme.getGenero());
        assertEquals(descricao, filme.getDescricao());
    }


    @Test
    void testGettersESetters() {
        FilmeConceitoDto filme = new FilmeConceitoDto();

        UUID uuid = UUID.randomUUID();
        String nome = "Filme Teste";
        String genero = "Ação";
        String descricao = "Descrição do filme teste";

        filme.setUuid(uuid);
        filme.setNome(nome);
        filme.setGenero(genero);
        filme.setDescricao(descricao);

        assertEquals(uuid, filme.getUuid());
        assertEquals(nome, filme.getNome());
        assertEquals(genero, filme.getGenero());
        assertEquals(descricao, filme.getDescricao());
    }

    @Test
    void testEqualsAndHashCode() {
        UUID uuid1 = UUID.randomUUID();
        UUID uuid2 = UUID.randomUUID();
        String nome = "Filme Teste";
        String genero = "Ação";
        String descricao = "Descrição do filme teste";

        FilmeConceitoDto filme1 = new FilmeConceitoDto(uuid1, nome, genero, descricao);
        FilmeConceitoDto filme2 = new FilmeConceitoDto(uuid1, nome, genero, descricao);
        FilmeConceitoDto filme3 = new FilmeConceitoDto(uuid2, nome, genero, descricao);


        assertEquals(filme1, filme1);

        assertEquals(filme1, filme2);

        assertEquals(false, filme1.equals(filme3));


        assertEquals(filme1.hashCode(), filme2.hashCode());

        assertEquals(false, Objects.equals(filme1.hashCode(), filme3.hashCode()));
    }
}
