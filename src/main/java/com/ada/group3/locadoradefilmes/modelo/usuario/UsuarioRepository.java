package com.ada.group3.locadoradefilmes.modelo.usuario;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface UsuarioRepository extends JpaRepository<Usuario,Long> {

    Optional<Usuario> findByUsername(String username);

    @Modifying
    @Query("update Usuario set isLate = true where username = :username")
    void marcarAtraso(@Param("username")String username);
    @Modifying
    @Query("update Usuario set isLate = false where username = :username")
    void desmarcarAtraso(@Param("username")String username);

}
