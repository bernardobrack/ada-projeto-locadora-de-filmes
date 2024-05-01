package com.ada.group3.locadoradefilmes.modelo.filme;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface FilmeRealRepository extends JpaRepository<FilmeReal, Long> {
    Optional<FilmeReal> findByUuid(UUID uuid);
    List<FilmeReal> findByFilmeConceitoUuid(UUID filmeConceitoUuid);

    @Query("SELECT f FROM FilmeReal f WHERE f.filmeConceito.uuid = :uuid AND f.isAlugado = :isAlugado")
    List<FilmeReal> findByUuidAndIsAlugado(@Param("uuid")UUID uuid, @Param("isAlugado")Boolean isAlugado);
}
