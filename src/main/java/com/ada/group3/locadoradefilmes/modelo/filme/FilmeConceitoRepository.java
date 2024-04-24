package com.ada.group3.locadoradefilmes.modelo.filme;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface FilmeConceitoRepository extends JpaRepository<FilmeConceito, Long> {

    Optional<FilmeConceito> findByNome(String nome);

    void deleteByNome(String nome);
}
