package com.ada.group3.locadoradefilmes.modelo.filme;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import java.util.List;

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

    public FilmeConceitoDto buscarPorNome(String nome) {
        return this.filmeConceitoRepository.findByNome(nome)
                .map(filme -> modelMapper.map(filme, FilmeConceitoDto.class))
                .orElseThrow(NaoEncontradoException::new);
    }

    public FilmeConceitoDto adicionarFilmeConceito(FilmeConceitoDto filmeConceitoDto) {
        FilmeConceito filmeConceito = modelMapper.map(filmeConceitoDto, FilmeConceito.class);
        FilmeConceito savedFilmeConceito = this.filmeConceitoRepository.save(filmeConceito);
        return modelMapper.map(savedFilmeConceito, FilmeConceitoDto.class);
    }

    public void atualizar(String nome, FilmeConceitoDto filmeConceitoAtualizado) {
        FilmeConceito filmeConceitoFound = this.filmeConceitoRepository.findByNome(nome).orElseThrow(NaoEncontradoException::new);
        FilmeConceito filmeConceitoEntity = modelMapper.map(filmeConceitoAtualizado, FilmeConceito.class);
        filmeConceitoEntity.setId(filmeConceitoFound.getId());
        this.filmeConceitoRepository.save(filmeConceitoEntity);
    }

    @Transactional
    public void excluir(String nome) {
        this.filmeConceitoRepository.deleteByNome(nome);
    }
}
