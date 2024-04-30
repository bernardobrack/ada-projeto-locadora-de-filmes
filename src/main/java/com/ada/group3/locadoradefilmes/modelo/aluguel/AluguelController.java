package com.ada.group3.locadoradefilmes.modelo.aluguel;


import com.ada.group3.locadoradefilmes.security.PermissionValidation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("${api.mapping}/alugueis")
public class AluguelController {

    private AluguelService aluguelService;

    public AluguelController(AluguelService aluguelService) {
        this.aluguelService = aluguelService;
    }
    //TODO: adicionar filtros: ver alugueis de um filme
    //TODO: autorizacao (no filme real tb)
    //TODO: so admin pode criar filmes reais e conceito

    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public List<AluguelDTO> listAll() {
        return aluguelService.findAll();
    }

    @GetMapping(params = "active")
    public List<AluguelDTO> listAllActiveOrInactive(@RequestParam Boolean active) {
        return aluguelService.listAllActiveOrInactive(active);
    }


    @GetMapping("/{aluguel_id}")
    @ResponseStatus(code = HttpStatus.OK)
    public AluguelDTO findById(@PathVariable UUID aluguel_id) {
        return this.aluguelService.findByUuid(aluguel_id);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public AluguelDTO save(@Valid @RequestBody AluguelRegistrationRequest aluguel, Authentication authentication) {
        if(PermissionValidation.validatePermission.apply(authentication, aluguel.usuarioLogin())) throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        return this.aluguelService.save(
                new AluguelDTO(
                        UUID.randomUUID(),
                        LocalDateTime.now(),
                        null,
                        aluguel.usuarioLogin(),
                        aluguel.filmeUuid()
                )
        );
    }

    @PatchMapping("/{aluguelId}")
    public AluguelDTO update(@PathVariable UUID aluguelId, Authentication authentication) {
        return this.aluguelService.refund(aluguelId, authentication);
    }
}
