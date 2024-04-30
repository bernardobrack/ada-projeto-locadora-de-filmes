package com.ada.group3.locadoradefilmes.modelo.aluguel;

import com.ada.group3.locadoradefilmes.exception.AluguelNaoEncontradoException;
import com.ada.group3.locadoradefilmes.exception.FilmeConceitoNaoEncontradoException;
import com.ada.group3.locadoradefilmes.exception.FilmeRealNaoEncontradoException;
import com.ada.group3.locadoradefilmes.exception.UsuarioNaoEncontradoException;
import com.ada.group3.locadoradefilmes.modelo.filme.FilmeReal;
import com.ada.group3.locadoradefilmes.modelo.filme.FilmeRealRepository;
import com.ada.group3.locadoradefilmes.modelo.usuario.Usuario;
import com.ada.group3.locadoradefilmes.modelo.usuario.UsuarioRepository;
import com.ada.group3.locadoradefilmes.security.PermissionValidation;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AluguelService {

    private final FilmeRealRepository filmeRealRepository;
    private final AluguelRepository aluguelRepository;
    private final UsuarioRepository usuarioRepository;

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
                        entity.getUsuario().getUsername(),
                        entity.getFilme().getUuid()
                )).toList();
    }

    public AluguelDTO save(AluguelDTO aluguelDTO) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findByUsername(aluguelDTO.getUsuarioLogin());
        Usuario usuario = usuarioOptional.orElseThrow(UsuarioNaoEncontradoException::new);
        FilmeReal filmeReal = filmeRealRepository.findByUuid(aluguelDTO.getFilmeUuid()).orElseThrow(FilmeRealNaoEncontradoException::new);
        //TODO: mudar excecoes abaixo
        if(usuario.getIsLate()) throw new RuntimeException("Usuário tem alugueis atrasados");
        if(usuario.getAlugueis().stream().anyMatch(aluguel -> aluguel.getHorarioDevolucao() == null)) throw new RuntimeException("Usuario tem alugueis pendentes");
        if(filmeReal.isAlugado()) throw new RuntimeException("Filme já está alugado");
        filmeReal.setAlugado(true);
        Aluguel aluguel = aluguelRepository.save(new Aluguel(null, aluguelDTO.getUuid(), aluguelDTO.getHorarioAluguel(), aluguelDTO.getHorarioDevolucao(),usuario, filmeReal));
        return new AluguelDTO(
                aluguel.getUuid(),
                aluguel.getHorarioAluguel(),
                aluguel.getHorarioDevolucao(),
                aluguel.getUsuario().getUsername(),
                aluguel.getFilme().getUuid()
        );
    }

    public AluguelDTO refund(UUID aluguelId, Authentication authentication) {
        Aluguel aluguel = aluguelRepository.findByUuid(aluguelId).orElseThrow(AluguelNaoEncontradoException::new);
        if(!PermissionValidation.validatePermission.apply(authentication, aluguel.getUsuario().getUsername())) {
            aluguel.setHorarioDevolucao(LocalDateTime.now());
            aluguel.getFilme().setAlugado(false);
            aluguel = aluguelRepository.save(aluguel);
            return new AluguelDTO(
                    aluguel.getUuid(),
                    aluguel.getHorarioAluguel(),
                    aluguel.getHorarioDevolucao(),
                    aluguel.getUsuario().getUsername(),
                    aluguel.getFilme().getUuid()
            );
        }
        throw new AccessDeniedException("Access denied");
    }
}
