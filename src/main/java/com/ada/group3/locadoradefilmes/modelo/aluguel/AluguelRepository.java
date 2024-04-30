package com.ada.group3.locadoradefilmes.modelo.aluguel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AluguelRepository extends JpaRepository<Aluguel, Long> {
    Optional<Aluguel> findByUuid(UUID uuid);
    List<Aluguel> findByHorarioDevolucaoIsNull();
    List<Aluguel> findByHorarioDevolucaoIsNotNull();
}
