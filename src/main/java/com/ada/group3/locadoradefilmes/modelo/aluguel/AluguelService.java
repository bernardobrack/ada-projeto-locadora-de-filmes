package com.ada.group3.locadoradefilmes.modelo.aluguel;

import com.ada.group3.locadoradefilmes.modelo.usuario.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AluguelService {

    private AluguelRepository aluguelRepository;
    private UsuarioRepository usuarioRepository;

    public AluguelService(AluguelRepository aluguelRepository) {
        this.aluguelRepository = aluguelRepository;
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
        return aluguelRepository.save(new Aluguel(null, aluguelDTO.getUuid(), aluguelDTO.getHorarioAluguel(), aluguelDTO.getHorarioDevolucao(),));
    }
}
