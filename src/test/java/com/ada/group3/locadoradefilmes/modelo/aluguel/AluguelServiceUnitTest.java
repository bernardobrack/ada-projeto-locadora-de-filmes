package com.ada.group3.locadoradefilmes.modelo.aluguel;

import com.ada.group3.locadoradefilmes.exception.AluguelNaoEncontradoException;
import com.ada.group3.locadoradefilmes.exception.FilmeRealNaoEncontradoException;
import com.ada.group3.locadoradefilmes.exception.UsuarioNaoEncontradoException;
import com.ada.group3.locadoradefilmes.modelo.filme.FilmeReal;
import com.ada.group3.locadoradefilmes.modelo.filme.FilmeRealRepository;
import com.ada.group3.locadoradefilmes.modelo.usuario.Usuario;
import com.ada.group3.locadoradefilmes.modelo.usuario.UsuarioRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;

import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class AluguelServiceUnitTest {

    @InjectMocks
    private AluguelService aluguelService;

    @Mock
    private AluguelRepository aluguelRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private FilmeRealRepository filmeRealRepository;

    private Aluguel aluguel;

    @BeforeEach
    public void setUp() {
        aluguel = new Aluguel();
        aluguel.setId(1L);
        aluguel.setHorarioAluguel(LocalDateTime.now().minus(Period.ofDays(1)));
        aluguel.setHorarioDevolucao(LocalDateTime.now());
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setUsername("test-user");
        usuario.setEmail("test@email.com");
        usuario.setAlugueis(List.of(aluguel));
        aluguel.setUsuario(usuario);
        FilmeReal filmeReal = new FilmeReal();
        filmeReal.setUuid(UUID.randomUUID());
        filmeReal.setAlugueis(List.of(aluguel));
        aluguel.setFilme(filmeReal);
        aluguel.setUuid(UUID.randomUUID());
    }

    @Test
    public void findAll_whenThereAreAluguel_mustReturnListOfAluguelDtoWithAllDtosAvailable() {
        List<Aluguel> aluguels = List.of(aluguel);
        Mockito.doReturn(aluguels)
                .when(aluguelRepository).findAll();

        List<AluguelDTO> response = aluguelService.findAll();
        Assertions.assertNotNull(response);
        Assertions.assertEquals(1, response.size());
        Assertions.assertEquals(aluguelToDto(aluguel), response.getFirst());
    }

    @Test
    public void findAll_whenThereAreNoAluguel_mustReturnEmptyList() {
        Mockito.doReturn(new ArrayList<>()).when(aluguelRepository).findAll();
        List<AluguelDTO> response = aluguelService.findAll();
        Assertions.assertNotNull(response);
        Assertions.assertEquals(0, response.size());
    }

    @Test
    public void findByUuid_whenUuidIsFound_mustReturnAluguelDtoWithUuid() {
        Mockito.doReturn(Optional.of(aluguel)).when(aluguelRepository).findByUuid(aluguel.getUuid());
        AluguelDTO response = aluguelService.findByUuid(aluguel.getUuid());
        Assertions.assertEquals(aluguelToDto(aluguel), response);
        Assertions.assertEquals(aluguel.getUuid(), response.getUuid());
    }

    @Test
    public void findByUuid_whenUuidIsNotFound_mustThrowAluguelNotFoundException() {
        Mockito.doReturn(Optional.empty()).when(aluguelRepository).findByUuid(aluguel.getUuid());
        Assertions.assertThrows(
                AluguelNaoEncontradoException.class,
                () -> aluguelService.findByUuid(aluguel.getUuid()),
                "Find by UUID when there is no corresponding Aluguel should throw exception"
        );

    }

    @Test
    public void findByUuid_whenUuidIsNull_mustThrowAluguelNotFoundException() {
        Mockito.doReturn(Optional.empty()).when(aluguelRepository).findByUuid(null);
        Assertions.assertThrows(
                AluguelNaoEncontradoException.class,
                () -> aluguelService.findByUuid(null),
                "Find by null UUID should throw exception"
        );
    }

    @Test
    public void listAllActive_whenThereIsCorrespondingAluguel_shouldReturnAListWithItsDtos() {
        Aluguel activeAluguel = new Aluguel(2L,UUID.randomUUID(),LocalDateTime.now(),null,new Usuario(),new FilmeReal());
        Mockito.doReturn(List.of(activeAluguel)).when(aluguelRepository).findByHorarioDevolucaoIsNull();

        List<AluguelDTO> response = aluguelService.listAllActiveOrInactive(true);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(aluguelToDto(activeAluguel), response.getFirst());

        Assertions.assertEquals(1, response.size());
        Assertions.assertNull(response.getFirst().getHorarioDevolucao());
    }

    @Test
    public void listAllInactive_whenThereIsCorrespondingAluguel_shouldReturnAListWithItsDtos() {
        Mockito.doReturn(List.of(aluguel)).when(aluguelRepository).findByHorarioDevolucaoIsNotNull();

        List<AluguelDTO> response = aluguelService.listAllActiveOrInactive(false);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(aluguelToDto(aluguel), response.getFirst());
        Assertions.assertEquals(1, response.size());
        Assertions.assertNotNull(response.getFirst().getHorarioDevolucao());
    }

    @Test
    public void listAllActive_whenThereIsNoCorrespondingAluguel_shouldReturnEmptyList() {

        Mockito.doReturn(List.of()).when(aluguelRepository).findByHorarioDevolucaoIsNull();

        List response = aluguelService.listAllActiveOrInactive(true);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(0, response.size());

    }

    @Test
    public void listAllInactive_whenThereIsNoCorrespondingAluguel_shouldReturnEmpyList() {
        Mockito.doReturn(List.of()).when(aluguelRepository).findByHorarioDevolucaoIsNotNull();

        List response = aluguelService.listAllActiveOrInactive(false);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(0, response.size());
    }

    @Test
    public void save_whenThereIsNoCorrespondingUsuario_mustThrowUsuarioNaoEncontradoException() {
        Mockito.doReturn(Optional.empty()).when(usuarioRepository).findByUsername(Mockito.any());
        Assertions.assertThrows(
                UsuarioNaoEncontradoException.class,
                () -> aluguelService.save(aluguelToDto(aluguel))
        );
    }

    @Test
    public void save_whenUsuarioIsLate_mustThrowRuntimeException() {
        Usuario lateUsuario = new Usuario();
        lateUsuario.setIsLate(true);
        Mockito.doReturn(Optional.of(lateUsuario)).when(usuarioRepository).findByUsername(Mockito.any());
        RuntimeException exception = Assertions.assertThrows(
                RuntimeException.class,
                () -> aluguelService.save(aluguelToDto(aluguel))
        );
        Assertions.assertEquals("Usuário tem alugueis atrasados", exception.getMessage());
    }



    @Test
    public void save_whenThereIsAnyPendingAluguel_mustThrowRuntimeException() {
        aluguel.setHorarioDevolucao(null);
        aluguel.getUsuario().setAlugueis(List.of(aluguel));
        aluguel.setUsuario(aluguel.getUsuario());
        aluguel.getFilme().setAlugueis(List.of(aluguel));
        aluguel.getFilme().setAlugado(true);

        Mockito.doReturn(Optional.of(aluguel.getUsuario())).when(usuarioRepository).findByUsername(Mockito.any());
        RuntimeException exception = Assertions.assertThrows(
          RuntimeException.class,
          () -> aluguelService.save(aluguelToDto(aluguel))
        );
        Assertions.assertEquals("Usuario tem alugueis pendentes", exception.getMessage());

    }

    @Test
    public void save_whenThereIsNoCorrespondingFilmeReal_mustThrowFilmeRealNaoEncontradoException() {
        Mockito.doReturn(Optional.of(aluguel.getUsuario())).when(usuarioRepository).findByUsername(Mockito.any());
        Mockito.doReturn(Optional.empty()).when(filmeRealRepository).findByUuid(Mockito.any());
        Assertions.assertThrows(
                FilmeRealNaoEncontradoException.class,
                () -> aluguelService.save(aluguelToDto(aluguel))
        );

    }

    @Test
    public void save_whenFilmeRealIsAlugado_mustThrowRuntimeException() {
        aluguel.getFilme().setAlugado(true);
        Mockito.doReturn(Optional.of(aluguel.getUsuario())).when(usuarioRepository).findByUsername(aluguel.getUsuario().getUsername());
        Mockito.doReturn(Optional.of(aluguel.getFilme())).when(filmeRealRepository).findByUuid(aluguel.getFilme().getUuid());
        RuntimeException exception = Assertions.assertThrows(
                RuntimeException.class,
                () -> aluguelService.save(aluguelToDto(aluguel))
        );
        Assertions.assertEquals("Filme já está alugado", exception.getMessage());
    }

    @Test
    public void save_whenSuccessful_mustChangeFilmeIsAlugadoToTrue() {
        Mockito.doReturn(Optional.of(aluguel.getUsuario())).when(usuarioRepository).findByUsername(aluguel.getUsuario().getUsername());
        Mockito.doReturn(Optional.of(aluguel.getFilme())).when(filmeRealRepository).findByUuid(aluguel.getFilme().getUuid());
        Mockito.doReturn(aluguel).when(aluguelRepository).save(Mockito.any());
        aluguelService.save(aluguelToDto(aluguel));
        Assertions.assertTrue(aluguel.getFilme().isAlugado());

    }

    @Test
    public void save_whenSuccessful_mustSaveTheRightAluguelToRepository() {
        Mockito.doReturn(Optional.of(aluguel.getUsuario())).when(usuarioRepository).findByUsername(aluguel.getUsuario().getUsername());
        Mockito.doReturn(Optional.of(aluguel.getFilme())).when(filmeRealRepository).findByUuid(aluguel.getFilme().getUuid());
        Mockito.doReturn(aluguel).when(aluguelRepository).save(Mockito.any());

        aluguelService.save(aluguelToDto(aluguel));
        ArgumentCaptor<Aluguel> aluguelArgumentCaptor = ArgumentCaptor.forClass(Aluguel.class);
        Mockito.verify(aluguelRepository, Mockito.times(1)).save(aluguelArgumentCaptor.capture());
        Aluguel called = aluguelArgumentCaptor.getValue();
        Assertions.assertEquals(aluguel.getUuid(), called.getUuid());
        Assertions.assertEquals(aluguel.getHorarioAluguel(), called.getHorarioAluguel());
        Assertions.assertEquals(aluguel.getHorarioDevolucao(), called.getHorarioDevolucao());
        Assertions.assertEquals(aluguel.getUsuario(), called.getUsuario());
        Assertions.assertEquals(aluguel.getFilme(), called.getFilme());
    }

    @Test
    public void save_whenSuccessful_mustReturnCorrespondingDto() {
        Mockito.doReturn(Optional.of(aluguel.getUsuario())).when(usuarioRepository).findByUsername(aluguel.getUsuario().getUsername());
        Mockito.doReturn(Optional.of(aluguel.getFilme())).when(filmeRealRepository).findByUuid(aluguel.getFilme().getUuid());
        Mockito.doReturn(aluguel).when(aluguelRepository).save(Mockito.any());

        AluguelDTO response = aluguelService.save(aluguelToDto(aluguel));
        Assertions.assertEquals(aluguelToDto(aluguel), response);

    }

    @Test
    public void refund_whenUuidHasNoCorrespondingAluguel_mustThrowAluguelNaoEncontradoException() {
        Mockito.doReturn(Optional.empty()).when(aluguelRepository).findByUuid(aluguel.getUuid());
        Assertions.assertThrows(
                AluguelNaoEncontradoException.class,
                () -> aluguelService.refund(aluguel.getUuid(), null)
        );
    }

    @Test
    public void refund_whenInvalidAuthenticationMustThrowAccessDeniedException() {
        Mockito.doReturn(Optional.of(aluguel)).when(aluguelRepository).findByUuid(aluguel.getUuid());
        var authentication = Mockito.mock(Authentication.class);
        Mockito.doReturn("invalid-username").when(authentication).getName();
        Mockito.doReturn(List.of()).when(authentication).getAuthorities();
        Assertions.assertThrows(
                AccessDeniedException.class,
                () -> aluguelService.refund(aluguel.getUuid(), authentication)
        );

    }

    @Test
    public void refund_whenAluguelIsInvalid_mustThrowRuntimeException() {
        Mockito.doReturn(Optional.of(aluguel)).when(aluguelRepository).findByUuid(aluguel.getUuid());
        var authentication = Mockito.mock(Authentication.class);
        Mockito.doReturn(aluguel.getUsuario().getUsername()).when(authentication).getName();
        RuntimeException exception = Assertions.assertThrows(
                RuntimeException.class,
                () ->  aluguelService.refund(aluguel.getUuid(), authentication)
        );
        Assertions.assertEquals("Aluguel já inativo", exception.getMessage());

    }

    @Test
    public void refund_whenSuccessful_mustSetAluguelHorarioDevolucaoToNow() {
        aluguel.setHorarioDevolucao(null);
        Mockito.doReturn(Optional.of(aluguel)).when(aluguelRepository).findByUuid(aluguel.getUuid());
        var authentication = Mockito.mock(Authentication.class);
        Mockito.doReturn(aluguel.getUsuario().getUsername()).when(authentication).getName();
        Mockito.doReturn(aluguel).when(aluguelRepository).save(aluguel);

        aluguelService.refund(aluguel.getUuid(), authentication);

        Assertions.assertNotNull(aluguel.getHorarioDevolucao());

    }

    @Test
    public void refund_whenSuccessful_mustSetFilmeAlugadoToFalse() {
        aluguel.setHorarioDevolucao(null);
        aluguel.getFilme().setAlugado(true);
        aluguel.getUsuario().setAlugueis(List.of(aluguel));
        Mockito.doReturn(Optional.of(aluguel)).when(aluguelRepository).findByUuid(aluguel.getUuid());
        var authentication = Mockito.mock(Authentication.class);
        Mockito.doReturn(aluguel.getUsuario().getUsername()).when(authentication).getName();
        Mockito.doReturn(aluguel).when(aluguelRepository).save(aluguel);

        aluguelService.refund(aluguel.getUuid(), authentication);

        Assertions.assertFalse(aluguel.getFilme().isAlugado());
    }

    @Test
    public void refund_whenSuccessful_mustUpdateRepositoryWithCorrectInformation() {
        aluguel.setHorarioDevolucao(null);
        aluguel.getFilme().setAlugado(true);
        aluguel.getUsuario().setAlugueis(List.of(aluguel));
        Mockito.doReturn(Optional.of(aluguel)).when(aluguelRepository).findByUuid(aluguel.getUuid());
        var authentication = Mockito.mock(Authentication.class);
        Mockito.doReturn(aluguel.getUsuario().getUsername()).when(authentication).getName();
        Mockito.doReturn(aluguel).when(aluguelRepository).save(aluguel);


        aluguelService.refund(aluguel.getUuid(), authentication);

        ArgumentCaptor<Aluguel> aluguelArgumentCaptor = ArgumentCaptor.forClass(Aluguel.class);
        Mockito.verify(aluguelRepository, Mockito.times(1)).save(aluguelArgumentCaptor.capture());
        var captured = aluguelArgumentCaptor.getValue();
        Assertions.assertEquals(aluguel.getUuid(), captured.getUuid());
        Assertions.assertEquals(aluguel.getHorarioAluguel(), captured.getHorarioAluguel());
        Assertions.assertNotNull(captured.getHorarioDevolucao());
        Assertions.assertEquals(aluguel.getUsuario(), captured.getUsuario());
        Assertions.assertEquals(aluguel.getFilme(), captured.getFilme());
        Assertions.assertFalse(aluguel.getFilme().isAlugado());
    }

    @Test
    public void refund_whenSuccessful_mustReturnCorrespondingAluguelDto() {
        aluguel.setHorarioDevolucao(null);
        aluguel.getFilme().setAlugado(true);
        aluguel.getUsuario().setAlugueis(List.of(aluguel));
        Mockito.doReturn(Optional.of(aluguel)).when(aluguelRepository).findByUuid(aluguel.getUuid());
        var authentication = Mockito.mock(Authentication.class);
        Mockito.doReturn(aluguel.getUsuario().getUsername()).when(authentication).getName();
        Mockito.doReturn(aluguel).when(aluguelRepository).save(aluguel);


        var response = aluguelService.refund(aluguel.getUuid(), authentication);
        Assertions.assertInstanceOf(AluguelDTO.class, response);
        Assertions.assertEquals(aluguel.getUuid(), response.getUuid());
        Assertions.assertEquals(aluguel.getHorarioAluguel(), response.getHorarioAluguel());
        Assertions.assertNotNull(response.getHorarioDevolucao());
        Assertions.assertEquals(aluguel.getUsuario().getUsername(), response.getUsuarioLogin());
        Assertions.assertEquals(aluguel.getFilme().getUuid(), response.getFilmeUuid());
    }

    private AluguelDTO aluguelToDto(Aluguel aluguel) {
        return new AluguelDTO(
                aluguel.getUuid(),
                aluguel.getHorarioAluguel(),
                aluguel.getHorarioDevolucao(),
                aluguel.getUsuario().getUsername(),
                aluguel.getFilme().getUuid()
        );
    }

}

