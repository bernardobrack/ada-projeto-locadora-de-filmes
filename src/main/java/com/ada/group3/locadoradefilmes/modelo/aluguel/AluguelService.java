package com.ada.group3.locadoradefilmes.modelo.aluguel;

import com.ada.group3.locadoradefilmes.exception.FilmeConceitoNaoEncontradoException;
import com.ada.group3.locadoradefilmes.exception.UsuarioNaoEncontradoException;
import com.ada.group3.locadoradefilmes.modelo.filme.FilmeReal;
import com.ada.group3.locadoradefilmes.modelo.filme.FilmeRealRepository;
import com.ada.group3.locadoradefilmes.modelo.usuario.Usuario;
import com.ada.group3.locadoradefilmes.modelo.usuario.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AluguelService {

    private final FilmeRealRepository filmeRealRepository;
    private AluguelRepository aluguelRepository;
    private UsuarioRepository usuarioRepository;

    public AluguelService(AluguelRepository aluguelRepository, UsuarioRepository usuarioRepository, FilmeRealRepository filmeRealRepository) {
        this.aluguelRepository = aluguelRepository;
        this.usuarioRepository = usuarioRepository;
        this.filmeRealRepository = filmeRealRepository;
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
        Usuario usuario = usuarioOptional.orElseThrow(UsuarioNaoEncontradoException::new);
        //TODO: mudar excecao abaixo
        FilmeReal filmeReal = filmeRealRepository.findByUuid(aluguelDTO.getFilmeUuid()).orElseThrow(FilmeConceitoNaoEncontradoException::new);
        aluguelRepository.save(new Aluguel(null, aluguelDTO.getUuid(), aluguelDTO.getHorarioAluguel(), aluguelDTO.getHorarioDevolucao(),usuario, filmeReal));
        return null;
    }
}
