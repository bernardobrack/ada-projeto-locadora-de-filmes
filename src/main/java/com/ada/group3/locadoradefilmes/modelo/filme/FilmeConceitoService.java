package com.ada.group3.locadoradefilmes.modelo.filme;

import com.ada.group3.locadoradefilmes.exception.NaoEncontradoException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FilmeConceitoService {

    private final com.ada.group3.locadoradefilmes.modelo.filme.FilmeConceitoRepository filmeConceitoRepository;
    private final ModelMapper modelMapper;

    public List<com.ada.group3.locadoradefilmes.modelo.filme.FilmeConceitoDto> listarTodos() {
        return this.filmeConceitoRepository.findAll()
                .stream()
                .map(filmeConceito -> modelMapper.map(filmeConceito, com.ada.group3.locadoradefilmes.modelo.filme.FilmeConceitoDto.class))
                .toList();
    }

//TODO confirmar exceção
    public com.ada.group3.locadoradefilmes.modelo.filme.FilmeConceitoDto buscarPorNome(String nome) {
        return this.filmeConceitoRepository.findByNome(nome)
                .map(filme -> modelMapper.map(filme, com.ada.group3.locadoradefilmes.modelo.filme.FilmeConceitoDto.class))
                .orElseThrow(NaoEncontradoException::new);
    }

    public com.ada.group3.locadoradefilmes.modelo.filme.FilmeConceitoDto adicionarFilmeConceito(com.ada.group3.locadoradefilmes.modelo.filme.FilmeConceitoDto filmeConceitoDto) {
        FilmeConceito filmeConceito = modelMapper.map(filmeConceitoDto, FilmeConceito.class);
        FilmeConceito savedFilmeConceito = this.filmeConceitoRepository.save(filmeConceito);
        return modelMapper.map(savedFilmeConceito, com.ada.group3.locadoradefilmes.modelo.filme.FilmeConceitoDto.class);
    }

    public void atualizar(String nome, com.ada.group3.locadoradefilmes.modelo.filme.FilmeConceitoDto filmeConceitoAtualizado) {
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
