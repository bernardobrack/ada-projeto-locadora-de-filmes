package com.ada.group3.locadoradefilmes.modelo.aluguel;

import com.ada.group3.locadoradefilmes.security.DataBaseAuthConfig;
import com.ada.group3.locadoradefilmes.security.PasswordEncoderConfig;
import com.ada.group3.locadoradefilmes.security.WebSecurityConfig;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Import({PasswordEncoderConfig.class, DataBaseAuthConfig.class, WebSecurityConfig.class})
public class AluguelControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @SpyBean
    private AluguelService aluguelService;

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




}
