package com.ada.group3.locadoradefilmes.modelo.aluguel;

import com.ada.group3.locadoradefilmes.modelo.filme.FilmeReal;
import com.ada.group3.locadoradefilmes.modelo.usuario.Usuario;
import com.ada.group3.locadoradefilmes.modelo.usuario.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AluguelService {

    private AluguelRepository aluguelRepository;
    private UsuarioRepository usuarioRepository;

    public AluguelService(AluguelRepository aluguelRepository, UsuarioRepository usuarioRepository) {
        this.aluguelRepository = aluguelRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public List<AluguelDTO> findAll() {
        return aluguelRepository.findAll().stream()
                .map(entity -> new AluguelDTO(
                        entity.getUuid(),
                        entity.getHorarioAluguel(),
                        entity.getHorarioDevolucao(),
                        entity.getUsuario().getLogin(),
                        entity.getFilme().getUuid()
                )).toList();
    }

    public AluguelDTO save(AluguelDTO aluguelDTO) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findByLogin(aluguelDTO.getUsuarioLogin());
        Usuario usuario = usuarioOptional.orElseThrow(EntityNotFoundException::new);
        aluguelRepository.save(new Aluguel(null, aluguelDTO.getUuid(), aluguelDTO.getHorarioAluguel(), aluguelDTO.getHorarioDevolucao(),usuario, new FilmeReal()));
        return null;
    }
}
