package com.ada.group3.locadoradefilmes.modelo.filme;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FilmeConceitoRepository extends JpaRepository<FilmeConceito, Integer> {

    Optional<FilmeConceito> findByUuid(UUID uuid);

    void deleteByUuid(UUID uuid);

    List<FilmeConceito> findByNomeContainingIgnoreCase(String nome);
}
