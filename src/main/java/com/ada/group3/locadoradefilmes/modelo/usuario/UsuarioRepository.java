package com.ada.group3.locadoradefilmes.modelo.usuario;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface UsuarioRepository extends JpaRepository<Usuario,Long> {

    Optional<Usuario> findByLogin(String Login);

    @Modifying
    @Query("update Usuario set temAtraso = true where login = :login")
    void marcarAtraso(@Param("login")String login);
    @Modifying
    @Query("update Usuario set temAtraso = false where login = :login")
    void desmarcarAtraso(@Param("login")String login);

    void deleteByLogin(String login);




}
