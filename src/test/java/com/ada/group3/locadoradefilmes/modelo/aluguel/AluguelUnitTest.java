package com.ada.group3.locadoradefilmes.modelo.aluguel;

import com.ada.group3.locadoradefilmes.modelo.filme.FilmeReal;
import com.ada.group3.locadoradefilmes.modelo.usuario.Usuario;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.Period;
import java.util.UUID;

public class AluguelUnitTest {

    private Aluguel aluguel;

    @BeforeEach
    public void setUp() {
        aluguel = new Aluguel();
        aluguel.setFilme(new FilmeReal());
        aluguel.setId(1L);
        aluguel.setUuid(UUID.randomUUID());
        aluguel.setHorarioAluguel(LocalDateTime.now().minus(Period.ofDays(3)));
        aluguel.setHorarioDevolucao(LocalDateTime.now());
        aluguel.setUsuario(new Usuario());
    }

    @Test
    public void getId_mustReturnId() {
        Assertions.assertEquals(1L, aluguel.getId());
    }

    @Test
    public void equals_shouldReturnTrueWhenObjectsAreTheSame() {
        Assertions.assertEquals(aluguel, aluguel);
    }

    @Test
    public void equals_shouldReturnFalseWhenObjectsAreDifferent() {
        Aluguel differentAluguel = new Aluguel();
        differentAluguel.setFilme(aluguel.getFilme());
        differentAluguel.setId(2L);
        differentAluguel.setUuid(aluguel.getUuid());
        differentAluguel.setHorarioAluguel(aluguel.getHorarioAluguel());
        differentAluguel.setHorarioDevolucao(aluguel.getHorarioDevolucao());
        differentAluguel.setUsuario(aluguel.getUsuario());
        Assertions.assertNotEquals(aluguel, differentAluguel);
        differentAluguel.setId(1L);

        differentAluguel.setUuid(UUID.randomUUID());
        Assertions.assertNotEquals(aluguel, differentAluguel);
        differentAluguel.setUuid(aluguel.getUuid());

        differentAluguel.setFilme(new FilmeReal());
        Assertions.assertNotEquals(aluguel, differentAluguel);
        differentAluguel.setFilme(aluguel.getFilme());

        differentAluguel.setHorarioAluguel(LocalDateTime.now());
        Assertions.assertNotEquals(aluguel, differentAluguel);
        differentAluguel.setHorarioAluguel(aluguel.getHorarioAluguel());

        differentAluguel.setUsuario(new Usuario());
        Assertions.assertNotEquals(aluguel, differentAluguel);
        differentAluguel.setUsuario(aluguel.getUsuario());

        differentAluguel.setHorarioDevolucao(LocalDateTime.now().plus(Period.ofDays(2)));
        Assertions.assertNotEquals(aluguel, differentAluguel);
        differentAluguel.setHorarioDevolucao(aluguel.getHorarioDevolucao());

    }

    @Test
    public void equals_whenObjectsAreEqual_shouldReturnTrue() {
        Aluguel differentAluguel = new Aluguel();
        differentAluguel.setFilme(aluguel.getFilme());
        differentAluguel.setId(1L);
        differentAluguel.setUuid(aluguel.getUuid());
        differentAluguel.setHorarioAluguel(aluguel.getHorarioAluguel());
        differentAluguel.setHorarioDevolucao(aluguel.getHorarioDevolucao());
        differentAluguel.setUsuario(aluguel.getUsuario());
        Assertions.assertEquals(aluguel, differentAluguel);
    }

    @Test
    public void equals_whenOneObjectIsNull_shouldReturnFalse() {
        Assertions.assertNotEquals(aluguel, null);
    }
}
