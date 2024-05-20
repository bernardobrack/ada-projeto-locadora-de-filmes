package com.ada.group3.locadoradefilmes.modelo.aluguel;

import com.ada.group3.locadoradefilmes.modelo.filme.*;
import com.ada.group3.locadoradefilmes.modelo.usuario.EmailValidation.EmailValidationResponse;
import com.ada.group3.locadoradefilmes.modelo.usuario.EmailValidation.EmailValidationService;
import com.ada.group3.locadoradefilmes.modelo.usuario.UsuarioRequest;
import com.ada.group3.locadoradefilmes.modelo.usuario.UsuarioService;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.mockito.Mockito;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;


public class TestDatabaseExtension implements BeforeAllCallback {

    private AluguelService aluguelService;

    private UsuarioService usuarioService;

    private FilmeConceitoService filmeConceitoService;

    private FilmeRealService filmeRealService;

    private EmailValidationService emailValidationService;

    @Override
    public void beforeAll(ExtensionContext extensionContext) throws Exception {
        ApplicationContext testContext = SpringExtension.getApplicationContext(extensionContext);
        aluguelService = testContext.getBean(AluguelService.class);
        usuarioService = testContext.getBean(UsuarioService.class);
        filmeConceitoService = testContext.getBean(FilmeConceitoService.class);
        filmeRealService = testContext.getBean(FilmeRealService.class);
        emailValidationService = testContext.getBean(EmailValidationService.class);
        EmailValidationResponse emailValidationResponse = new EmailValidationResponse();
        emailValidationResponse.setFormat_valid(true);
        emailValidationResponse.setMx_found(true);
        Mockito.doReturn(emailValidationResponse).when(emailValidationService).validarEmail(Mockito.eq("user@test.com"), Mockito.any());
        FilmeConceitoRequest request = new FilmeConceitoRequest();
        request.setGenero("Genero");
        request.setNome("Filme teste");
        request.setDescricao("Um filme que nao existe");
        FilmeConceitoDto filmeConceitoDto = filmeConceitoService.adicionarFilmeConceito(request);
        FilmeReal filmeReal = filmeRealService.save(filmeConceitoDto.getUuid());
        UsuarioRequest usuarioRequest = new UsuarioRequest();
        usuarioRequest.setName("test-name");
        usuarioRequest.setLastName("test-lastname");
        usuarioRequest.setEmail("user@test.com");
        usuarioRequest.setPassword("St@rG@z3r^#");
        usuarioRequest.setCpf("13215056984");
        usuarioRequest.setUsername("test-username");
        usuarioService.adicionarUsuario(usuarioRequest);
        AluguelControllerIntegrationTest.filmeConceitoUuid = filmeConceitoDto.getUuid();
        AluguelControllerIntegrationTest.filmeRealUuid = filmeReal.getUuid();
        AluguelControllerIntegrationTest.usuarioUsername = usuarioRequest.getUsername();
    }
}
