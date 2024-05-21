package com.ada.group3.locadoradefilmes.modelo.aluguel;

import com.ada.group3.locadoradefilmes.modelo.filme.*;
import com.ada.group3.locadoradefilmes.modelo.usuario.EmailValidation.EmailValidationResponse;
import com.ada.group3.locadoradefilmes.modelo.usuario.EmailValidation.EmailValidationService;
import com.ada.group3.locadoradefilmes.modelo.usuario.UsuarioRepository;
import com.ada.group3.locadoradefilmes.modelo.usuario.UsuarioRequest;
import com.ada.group3.locadoradefilmes.modelo.usuario.UsuarioService;
import com.ada.group3.locadoradefilmes.security.DataBaseAuthConfig;
import com.ada.group3.locadoradefilmes.security.PasswordEncoderConfig;
import com.ada.group3.locadoradefilmes.security.WebSecurityConfig;
import com.jayway.jsonpath.JsonPath;
import jakarta.annotation.PostConstruct;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(TestDatabaseExtension.class)
@Import({PasswordEncoderConfig.class, DataBaseAuthConfig.class, WebSecurityConfig.class})
public class AluguelControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @SpyBean
    private AluguelService aluguelService;

    @SpyBean
    private UsuarioService usuarioService;

    @SpyBean
    private FilmeConceitoService filmeConceitoService;

    @SpyBean
    private FilmeRealService filmeRealService;

    @MockBean
    private EmailValidationService emailValidationService;

    private static AluguelDTO aluguel;
    public static UUID filmeConceitoUuid;
    public static UUID filmeRealUuid;
    public static String usuarioUsername;



    @Test
    @Order(1)
    @WithMockUser(roles = {"CLIENTE"})
    public void listAll_whenAuthenticated_mustReturn200() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/v1/alugueis")
                        .accept(MediaType.APPLICATION_JSON)
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @Order(2)
    public void listAll_whenUnauthenticated_mustReturn401() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/v1/alugueis")
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    @Order(3)
    public void listAll_whenUnauthenticated_mustReturnEmptyBody() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/v1/alugueis")
                .accept(MediaType.APPLICATION_JSON)
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$").doesNotExist());
    }

    @Test
    @Order(4)
    @WithMockUser(roles = {"CLIENTE"})
    public void listAll_whenAuthenticated_mustcallServiceFindAll() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/v1/alugueis")
                .accept(MediaType.APPLICATION_JSON)
        ).andDo(MockMvcResultHandlers.print());

        Mockito.verify(aluguelService, Mockito.times(1)).findAll();
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    @Order(5)
    public void listAllActiveOrInactive_whenUnauthenticated_mustReturn401(Boolean active) throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/v1/alugueis")
                        .param("active", active.toString())
                        .accept(MediaType.APPLICATION_JSON)
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    @Order(6)
    public void listAllActiveOrInactive_whenUnauthenticated_mustReturnEmptyBody(Boolean active) throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/v1/alugueis")
                                .param("active", active.toString())
                                .accept(MediaType.APPLICATION_JSON)
                ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$").doesNotExist());
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    @Order(7)
    @WithMockUser(roles = {"CLIENTE"})
    public void listAllActiveOrInactive_whenAuthenticated_mustCallServiceListAllActiveOrInactive(Boolean active) throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/v1/alugueis")
                .param("active", active.toString())
                .accept(MediaType.APPLICATION_JSON)
        ).andDo(MockMvcResultHandlers.print());
        Mockito.verify(aluguelService, Mockito.times(1)).listAllActiveOrInactive(active);

    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    @Order(8)
    @WithMockUser(roles = {"CLIENTE"})
    public void listAllActiveOrInactive_whenAuthenticated_mustReturnAnOk(Boolean active) throws Exception{
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/v1/alugueis")
                        .param("active", active.toString())
                        .accept(MediaType.APPLICATION_JSON)
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @Order(9)
    public void findById_whenUnauthenticated_mustReturn401() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/v1/alugueis/1")
                        .accept(MediaType.APPLICATION_JSON)
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    @Order(10)
    public void findById_whenUnauthenticated_mustReturnEmptyBody() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/v1/alugueis/1")
                .accept(MediaType.APPLICATION_JSON)
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$").doesNotExist());
    }

    @Test
    @Order(11)
    @WithMockUser(roles = {"CLIENTE"})
    public void findById_whenAuthenticated_mustcallServiceFindByUuid() throws Exception {
        UUID uuid = UUID.randomUUID();
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/v1/alugueis/%s".formatted(uuid))
                .accept(MediaType.APPLICATION_JSON)
        );

        Mockito.verify(aluguelService, Mockito.times(1)).findByUuid(uuid);
    }

    @Test
    @Order(12)
    @WithMockUser(roles = {"CLIENTE"})
    public void findById_whenNoAluguelIsFound_mustReturnNotFound() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/v1/alugueis/%s".formatted(UUID.randomUUID()))
                        .accept(MediaType.APPLICATION_JSON)
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @Order(12)
    @WithMockUser(roles = {"CLIENTE"})
    public void findById_whenNoAluguelIsFound_mustReturnErrorBody() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/v1/alugueis/%s".formatted(UUID.randomUUID()))
                .accept(MediaType.APPLICATION_JSON)
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.exceptionClass").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.httpStatus").value("NOT_FOUND"));
    }

    @Test
    @Order(13)
    public void save_whenUnauthenticated_mustReturn401() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/v1/alugueis")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content("""
                          {
                            "usuarioLogin": "test-user",
                            "filmeUuid": %s
                          }
                        """.formatted(UUID.randomUUID())
                        )
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    @Order(14)
    @WithMockUser(username = "another-user")
    public void save_whenAuthenticatedWithDifferentUsername_mustReturnForbidden() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/v1/alugueis")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON).content(
                                """
                                {
                                  "usuarioLogin": "test-user",
                                  "filmeUuid": "%s"
                                }

                                """.formatted(UUID.randomUUID())
                        )
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    @Order(15)
    @WithMockUser(username = "test-user")
    public void save_whenAuthenticatedWithSameUser_mustReturnCallServiceSaveAndReturnCorrespondingDTO() throws Exception {
        UUID filmeUuid = UUID.randomUUID();
        Mockito.doReturn(new AluguelDTO(
                null,
                LocalDateTime.now(),
                null,
                "test-user",
                filmeUuid
        )).when(aluguelService).save(Mockito.any());
        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/v1/alugueis")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON).content(
                                """
                                {
                                  "usuarioLogin": "test-user",
                                  "filmeUuid": "%s"
                                }

                                """.formatted(filmeUuid)
                        )
        ).andDo(MockMvcResultHandlers.print());
        ArgumentCaptor<AluguelDTO> captor = ArgumentCaptor.forClass(AluguelDTO.class);
        Mockito.verify(aluguelService, Mockito.times(1)).save(captor.capture());
        AluguelDTO aluguelDTO = captor.getValue();
        Assertions.assertEquals("test-user", aluguelDTO.getUsuarioLogin());
        Assertions.assertEquals(filmeUuid, aluguelDTO.getFilmeUuid());
    }

    @Test
    @Order(16)
    @WithMockUser(username = "admin-test-user", roles = {"ADMIN"})
    public void save_whenAuthenticatedAsAdmin_mustReturnCallServiceSaveAndReturnCorrespondingDTO() throws Exception {
        UUID filmeUuid = UUID.randomUUID();
        Mockito.doReturn(new AluguelDTO(
                null,
                LocalDateTime.now(),
                null,
                "test-user",
                filmeUuid
        )).when(aluguelService).save(Mockito.any());
        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/v1/alugueis")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON).content(
                                """
                                {
                                  "usuarioLogin": "test-user",
                                  "filmeUuid": "%s"
                                }

                                """.formatted(filmeUuid)
                        )
        ).andDo(MockMvcResultHandlers.print());
        ArgumentCaptor<AluguelDTO> captor = ArgumentCaptor.forClass(AluguelDTO.class);
        Mockito.verify(aluguelService, Mockito.times(1)).save(captor.capture());
        AluguelDTO aluguelDTO = captor.getValue();
        Assertions.assertEquals("test-user", aluguelDTO.getUsuarioLogin());
        Assertions.assertEquals(filmeUuid, aluguelDTO.getFilmeUuid());
    }

    @Test
    @Order(17)
    public void update_whenUnauthenticated_mustReturn401() throws Exception {
        UUID uuid = UUID.randomUUID();
        mockMvc.perform(
                MockMvcRequestBuilders.put("/api/v1/alugueis/%s".formatted(uuid))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    @Order(18)
    public void update_whenUnauthenticated_mustReturnEmptyBody() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.put("/api/v1/alugueis/%s".formatted(UUID.randomUUID()))
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$").doesNotExist());
    }

    @Test
    @Order(19)
    @WithMockUser(roles = "CLIENTE", username = "test-username")
    public void save_whenSuccessful_mustReturn201AndAluguelDto() throws Exception {
        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders.post("/api/v1/alugueis")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                        .content(
                                """
                                {
                                  "usuarioLogin": "%s",
                                  "filmeUuid": "%s"
                                }
                                """.formatted(usuarioUsername, filmeRealUuid)
                        )
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.uuid").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.horarioAluguel").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.usuarioLogin").value(usuarioUsername))
                .andExpect(MockMvcResultMatchers.jsonPath("$.filmeUuid").value(filmeRealUuid.toString()))
                .andReturn();
        String jsonResponse = result.getResponse().getContentAsString();
        UUID uuid = UUID.fromString(JsonPath.read(jsonResponse, "$.uuid"));
        LocalDateTime horarioAluguel = LocalDateTime.parse(JsonPath.read(jsonResponse, "$.horarioAluguel"));
        String usuarioLogin = JsonPath.read(jsonResponse, "$.usuarioLogin");
        UUID filmeUuid = UUID.fromString(JsonPath.read(jsonResponse, "$.filmeUuid"));
        aluguel = new AluguelDTO(
                uuid,
                horarioAluguel,
                null,
                usuarioLogin,
                filmeUuid
        );
    }

    @Test
    @Order(20)
    @WithMockUser(roles = "CLIENTE", username = "test-username")
    public void listAll_whenSuccessful_mustReturnExistingDtos() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/v1/alugueis")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].usuarioLogin").value(usuarioUsername))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].filmeUuid").value(filmeRealUuid.toString()));
    }

    @Test
    @Order(21)
    @WithMockUser(roles = "CLIENTE", username = "test-username")
    public void listAllActive_whenThereIsActive_mustReturnOnlyActiveDtos() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/v1/alugueis?active=true")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].usuarioLogin").value(usuarioUsername))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].filmeUuid").value(filmeRealUuid.toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].horarioDevolucao").doesNotExist());
    }

    @Test
    @Order(22)
    @WithMockUser(roles = "CLIENTE", username = "test-username")
    public void listAllInactive_whenThereAreNoInactive_mustReturnOnlyInactiveDtos() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/v1/alugueis?active=false")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(0));
    }

    @Test
    @Order(23)
    @WithMockUser(roles = "CLIENTE", username = "test-username")
    public void findById_whenThereIsCorrespondingId_mustReturnAluguelDTO() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/v1/alugueis/%s".formatted(aluguel.getUuid().toString()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.uuid").value(aluguel.getUuid().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.usuarioLogin").value(aluguel.getUsuarioLogin()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.filmeUuid").value(aluguel.getFilmeUuid().toString()));
    }

    @Test
    @Order(24)
    @WithMockUser(roles = "CLIENTE", username = "test-username")
    public void update_whenExistsCorrespondingAluguel_mustReturnItWithNewHorarioDevolucao() throws Exception {
        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders.patch("/api/v1/alugueis/%s".formatted(aluguel.getUuid().toString()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.uuid").value(aluguel.getUuid().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.usuarioLogin").value(aluguel.getUsuarioLogin()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.filmeUuid").value(aluguel.getFilmeUuid().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.horarioDevolucao").isString())
                .andExpect(MockMvcResultMatchers.jsonPath("$.horarioAluguel").isString())
                .andReturn();

        aluguel.setHorarioDevolucao(
                LocalDateTime.parse(JsonPath.read(result.getResponse().getContentAsString(), "$.horarioDevolucao"))
        );
    }

}
