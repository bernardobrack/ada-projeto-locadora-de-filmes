package com.ada.group3.locadoradefilmes.modelo.filme;

import com.ada.group3.locadoradefilmes.exception.FilmeConceitoNaoEncontradoException;
import com.ada.group3.locadoradefilmes.exception.NaoEncontradoException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FilmeConceitoService {

    private final FilmeConceitoRepository filmeConceitoRepository;
    private final ModelMapper modelMapper;

    public List<FilmeConceitoDto> listarTodos() {
        return this.filmeConceitoRepository.findAll()
                .stream()
                .map(filmeConceito -> modelMapper.map(filmeConceito, FilmeConceitoDto.class))
                .toList();
    }

    public List<FilmeConceitoDto> listarPorTitulo(String titulo) {
        return filmeConceitoRepository.findByNomeContainingIgnoreCase(titulo)
                .stream()
                .map(filmeConceito -> modelMapper.map(filmeConceito, FilmeConceitoDto.class))
                .toList();
    }

    public FilmeConceitoDto buscarPorUUID(UUID uuid) {
        return this.filmeConceitoRepository.findByUuid(uuid)
                .map(filme -> modelMapper.map(filme, FilmeConceitoDto.class))
                .orElseThrow(FilmeConceitoNaoEncontradoException::new);
    }

    public FilmeConceitoDto adicionarFilmeConceito(FilmeConceitoRequest filmeConceitoRequest) {
        FilmeConceito filmeConceito = modelMapper.map(filmeConceitoRequest, FilmeConceito.class);
        filmeConceito.setUuid(UUID.randomUUID());
        FilmeConceito savedFilmeConceito = this.filmeConceitoRepository.save(filmeConceito);
        return modelMapper.map(savedFilmeConceito, FilmeConceitoDto.class);
    }

    public void atualizar(UUID uuid, FilmeConceitoDto filmeConceitoAtualizado) {
        FilmeConceito filmeConceitoFound = this.filmeConceitoRepository.findByUuid(uuid).orElseThrow(FilmeConceitoNaoEncontradoException::new);
        FilmeConceito filmeConceitoEntity = modelMapper.map(filmeConceitoAtualizado, FilmeConceito.class);
        filmeConceitoEntity.setId(filmeConceitoFound.getId());
        this.filmeConceitoRepository.save(filmeConceitoEntity);
    }

    @Transactional
    public void excluir(UUID uuid) {
        this.filmeConceitoRepository.deleteByUuid(uuid);
    }
}
