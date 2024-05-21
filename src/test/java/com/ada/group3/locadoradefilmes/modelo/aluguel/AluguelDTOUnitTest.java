package com.ada.group3.locadoradefilmes.modelo.aluguel;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.Period;
import java.util.UUID;

public class AluguelDTOUnitTest {

    private AluguelDTO aluguelDTO;

    @BeforeEach
    public void setUp() {
        aluguelDTO = new AluguelDTO();
        aluguelDTO.setFilmeUuid(UUID.randomUUID());
        aluguelDTO.setUuid(UUID.randomUUID());
        aluguelDTO.setHorarioAluguel(LocalDateTime.now().minus(Period.ofDays(3)));
        aluguelDTO.setHorarioDevolucao(LocalDateTime.now());
        aluguelDTO.setUsuarioLogin("test-username");
    }

    @Test
    public void toString_mustReturnStringRepresentation() {
        String expectedString = "AluguelDTO(uuid=%s, horarioAluguel=%s, horarioDevolucao=%s, usuarioLogin=%s, filmeUuid=%s)"
                .formatted(
                        aluguelDTO.getUuid().toString(),
                        aluguelDTO.getHorarioAluguel(),
                        aluguelDTO.getHorarioDevolucao(),
                        aluguelDTO.getUsuarioLogin(),
                        aluguelDTO.getFilmeUuid().toString()
                        );
        String stringRepresentation = aluguelDTO.toString();

        Assertions.assertInstanceOf(String.class, stringRepresentation);
        Assertions.assertEquals(expectedString, stringRepresentation);
    }

    @Test
    public void equals_shouldReturnTrueForSameObject() {
        Assertions.assertEquals(aluguelDTO, aluguelDTO);
    }

    @Test
    public void equals_shouldReturnFalseForDifferentObject() {
        AluguelDTO anotherAluguelDTO = new AluguelDTO();
        anotherAluguelDTO.setUuid(aluguelDTO.getUuid());
        anotherAluguelDTO.setHorarioAluguel(aluguelDTO.getHorarioAluguel());
        anotherAluguelDTO.setHorarioDevolucao(aluguelDTO.getHorarioDevolucao());
        anotherAluguelDTO.setUsuarioLogin("test-username-2");
        anotherAluguelDTO.setFilmeUuid(aluguelDTO.getFilmeUuid());
        Assertions.assertNotEquals(aluguelDTO, anotherAluguelDTO);

        anotherAluguelDTO.setUsuarioLogin("test-username");
        anotherAluguelDTO.setHorarioAluguel(null);
        Assertions.assertNotEquals(aluguelDTO, anotherAluguelDTO);

        anotherAluguelDTO.setHorarioAluguel(aluguelDTO.getHorarioAluguel());
        anotherAluguelDTO.setHorarioDevolucao(null);
        Assertions.assertNotEquals(aluguelDTO, anotherAluguelDTO);

        anotherAluguelDTO.setHorarioDevolucao(aluguelDTO.getHorarioDevolucao());
        anotherAluguelDTO.setUuid(UUID.randomUUID());
        Assertions.assertNotEquals(aluguelDTO, anotherAluguelDTO);

        anotherAluguelDTO.setUuid(aluguelDTO.getUuid());
        anotherAluguelDTO.setFilmeUuid(UUID.randomUUID());
        Assertions.assertNotEquals(aluguelDTO, anotherAluguelDTO);
    }

    @Test
    public void equals_shouldReturnTrueForEqualObject() {
        AluguelDTO anotherAluguelDTO = new AluguelDTO();
        anotherAluguelDTO.setUuid(aluguelDTO.getUuid());
        anotherAluguelDTO.setHorarioAluguel(aluguelDTO.getHorarioAluguel());
        anotherAluguelDTO.setHorarioDevolucao(aluguelDTO.getHorarioDevolucao());
        anotherAluguelDTO.setUsuarioLogin("test-username");
        anotherAluguelDTO.setFilmeUuid(aluguelDTO.getFilmeUuid());
        Assertions.assertEquals(aluguelDTO, anotherAluguelDTO);
    }

    @Test
    public void equals_whenOneObjectIsNull_shouldReturnFalse() {
        Assertions.assertNotEquals(aluguelDTO, null);
    }
}
