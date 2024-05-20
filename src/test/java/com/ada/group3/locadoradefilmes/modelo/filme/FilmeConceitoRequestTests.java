package com.ada.group3.locadoradefilmes.modelo.filme;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FilmeConceitoRequestTests {

    private static Validator validator;

    @BeforeAll
    public static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testFilmeConceitoRequestValid() {
        FilmeConceitoRequest request = new FilmeConceitoRequest("Nome", "Genero", "Descricao");
        Set<ConstraintViolation<FilmeConceitoRequest>> violations = validator.validate(request);

        assertTrue(violations.isEmpty(), "Expected no violations but got " + violations);
    }

    @Test
    public void testFilmeConceitoRequestInvalidNome() {
        FilmeConceitoRequest request = new FilmeConceitoRequest("", "Genero", "Descricao");
        Set<ConstraintViolation<FilmeConceitoRequest>> violations = validator.validate(request);

        assertEquals(1, violations.size());
        assertTrue(violations.iterator().next().getMessage().contains("Nome deve conter letras de A a Z e/ou números entre 1 e 100 caracteres"));
    }

    @Test
    public void testFilmeConceitoRequestInvalidGenero() {
        FilmeConceitoRequest request = new FilmeConceitoRequest("Nome", "", "Descricao");
        Set<ConstraintViolation<FilmeConceitoRequest>> violations = validator.validate(request);

        assertEquals(1, violations.size());
        assertTrue(violations.iterator().next().getMessage().contains("Genero deve conter letras de A a Z e/ou números entre 1 e 100 caracteres"));
    }

    @Test
    public void testFilmeConceitoRequestInvalidDescricao() {
        FilmeConceitoRequest request = new FilmeConceitoRequest("Nome", "Genero", "");
        Set<ConstraintViolation<FilmeConceitoRequest>> violations = validator.validate(request);

        assertEquals(1, violations.size());
        assertTrue(violations.iterator().next().getMessage().contains("Descrição deve conter letras de A a Z e/ou números entre 1 e 144 caracteres"));
    }

    @Test
    public void testNomeTooLong() {
        String longNome = "A".repeat(101);
        FilmeConceitoRequest request = new FilmeConceitoRequest(longNome, "Genero", "Descricao");
        Set<ConstraintViolation<FilmeConceitoRequest>> violations = validator.validate(request);

        assertEquals(1, violations.size());
        assertTrue(violations.iterator().next().getMessage().contains("Nome deve conter letras de A a Z e/ou números entre 1 e 100 caracteres"));
    }

    @Test
    public void testGeneroTooLong() {
        String longGenero = "A".repeat(101);
        FilmeConceitoRequest request = new FilmeConceitoRequest("Nome", longGenero, "Descricao");
        Set<ConstraintViolation<FilmeConceitoRequest>> violations = validator.validate(request);

        assertEquals(1, violations.size());
        assertTrue(violations.iterator().next().getMessage().contains("Genero deve conter letras de A a Z e/ou números entre 1 e 100 caracteres"));
    }

    @Test
    public void testDescricaoTooLong() {
        String longDescricao = "A".repeat(145);
        FilmeConceitoRequest request = new FilmeConceitoRequest("Nome", "Genero", longDescricao);
        Set<ConstraintViolation<FilmeConceitoRequest>> violations = validator.validate(request);

        assertEquals(1, violations.size());
        assertTrue(violations.iterator().next().getMessage().contains("Descrição deve conter letras de A a Z e/ou números entre 1 e 144 caracteres"));
    }

    @Test
    public void testNomeWithInvalidCharacters() {
        FilmeConceitoRequest request = new FilmeConceitoRequest("Nome@!", "Genero", "Descricao");
        Set<ConstraintViolation<FilmeConceitoRequest>> violations = validator.validate(request);

        assertEquals(1, violations.size());
        assertTrue(violations.iterator().next().getMessage().contains("Nome deve conter letras de A a Z e/ou números entre 1 e 100 caracteres"));
    }

    @Test
    public void testGeneroWithInvalidCharacters() {
        FilmeConceitoRequest request = new FilmeConceitoRequest("Nome", "Genero@!", "Descricao");
        Set<ConstraintViolation<FilmeConceitoRequest>> violations = validator.validate(request);

        assertEquals(1, violations.size());
        assertTrue(violations.iterator().next().getMessage().contains("Genero deve conter letras de A a Z e/ou números entre 1 e 100 caracteres"));
    }

    @Test
    public void testNomeNull() {
        FilmeConceitoRequest request = new FilmeConceitoRequest(null, "Genero", "Descricao");
        Set<ConstraintViolation<FilmeConceitoRequest>> violations = validator.validate(request);

        assertEquals(1, violations.size());
        assertTrue(violations.iterator().next().getMessage().contains("Nome deve conter letras de A a Z e/ou números entre 1 e 100 caracteres"));
    }

    @Test
    public void testGeneroNull() {
        FilmeConceitoRequest request = new FilmeConceitoRequest("Nome", null, "Descricao");
        Set<ConstraintViolation<FilmeConceitoRequest>> violations = validator.validate(request);

        assertEquals(1, violations.size());
        assertTrue(violations.iterator().next().getMessage().contains("Genero deve conter letras de A a Z e/ou números entre 1 e 100 caracteres"));
    }

    @Test
    public void testDescricaoNull() {
        FilmeConceitoRequest request = new FilmeConceitoRequest("Nome", "Genero", null);
        Set<ConstraintViolation<FilmeConceitoRequest>> violations = validator.validate(request);

        assertEquals(1, violations.size());
        assertTrue(violations.iterator().next().getMessage().contains("Descrição não pode estar em branco"));
    }
}
