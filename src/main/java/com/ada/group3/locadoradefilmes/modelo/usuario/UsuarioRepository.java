package com.ada.group3.locadoradefilmes.modelo.usuario;



import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario,Long> {

    Optional<Usuario> findByLogin(String Login);

    void deleteByLogin(String login);




}
