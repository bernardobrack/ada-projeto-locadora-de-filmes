package com.ada.group3.locadoradefilmes.modelo.filme;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${api.mapping}/filmes-reais")
@RequiredArgsConstructor
public class FilmeRealController {

    private final FilmeRealService filmeRealService;

    @PostMapping
    public void cadastrarFilme(@Valid @RequestBody FilmeRealRequest filmeRealRequest) {
        //TODO: adicionar retorno
        filmeRealService.save(filmeRealRequest.filmeConceitoId());
    }

}
