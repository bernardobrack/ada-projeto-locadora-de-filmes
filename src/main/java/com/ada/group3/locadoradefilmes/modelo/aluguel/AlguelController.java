package com.ada.group3.locadoradefilmes.modelo.aluguel;


import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("${api.mapping}/alugueis")
public class AlguelController {

    private AluguelService aluguelService;

    public AlguelController(AluguelService aluguelService) {
        this.aluguelService = aluguelService;
    }

    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public List<AluguelDTO> listAll() {
        return aluguelService.findAll();
    }
    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public AluguelDTO save(@Valid @RequestBody AluguelRegistrationRequest aluguel) {
        this.aluguelService.save(
                new AluguelDTO(
                        UUID.randomUUID(),
                        LocalDateTime.now(),
                        null,
                        aluguel.usuarioLogin(),
                        aluguel.filmeUuid()
                )
        );
        return null;
    }
}
