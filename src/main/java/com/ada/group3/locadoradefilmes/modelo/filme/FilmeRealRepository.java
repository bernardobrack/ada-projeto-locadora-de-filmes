package com.ada.group3.locadoradefilmes.modelo.filme;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FilmeRealRepository extends JpaRepository<FilmeReal, Long> {
}
