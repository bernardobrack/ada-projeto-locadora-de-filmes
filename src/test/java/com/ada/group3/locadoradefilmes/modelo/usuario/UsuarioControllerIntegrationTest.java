package com.ada.group3.locadoradefilmes.modelo.usuario;
import com.ada.group3.locadoradefilmes.security.DataBaseAuthConfig;
import com.ada.group3.locadoradefilmes.security.PasswordEncoderConfig;
import com.ada.group3.locadoradefilmes.security.WebSecurityConfig;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
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
public class UsuarioControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @SpyBean
    private UsuarioService service;


    @Test
    @Order(1)
    public void criarUsuario_DeveTerSucesso() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/v1/usuarios")
                                .content("""
                                        {
                                             "name": "Pedro",
                                             "lastName": "Silva",
                                             "cpf": "27347037000",
                                             "email": "pedrosilva@gmail.com",
                                             "username": "pedro.silva",
                                             "password": "St@rG@z3r^#"
                                        }
                                        """)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated());
        Mockito.verify(service, Mockito.times(1)).adicionarUsuario(Mockito.any());
    }

    @Test
    @Order(2)
    public void criarUsuarioSemNome_deveRetornarException() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/v1/usuarios")
                                .content("""
                                        {
                                             "name": "",
                                             "lastName": "paulo",
                                             "cpf": "27347037000",
                                             "email": "marcospaulo@gmail.com",
                                             "username": "marcos.paulo",
                                             "password": "St@rG@z3r^#"
                                        }
                                        """)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
        Mockito.verify(service, Mockito.never()).adicionarUsuario(Mockito.any());
    }

    @Test
    @Order(3)
    @WithMockUser(roles = "ADMIN")
    public void buscarUsuarios_deveRetornarListaDeUsuarios() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/v1/usuarios")
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(
                        MockMvcResultMatchers.status().isOk()
                );

    }

    @Test
    @Order(4)
    @WithMockUser(roles = "CLIENTE")
    public void buscarUsuariosSemSerAdmin_deveRetornarException() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/v1/usuarios")
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(
                        MockMvcResultMatchers.status().isForbidden()
                );
    }

    @Test
    @Order(5)
    @WithMockUser(username = "pablo.marcelo", roles = "CLIENTE")
    public void buscarUsuarioSemSerOMesmo_deveRetornarException() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/v1/usuarios/pedro.silva")
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(
                        MockMvcResultMatchers.status().isForbidden()
                );

    }

    @Test
    @Order(6)
    @WithMockUser(username = "pedro.silva", roles = "CLIENTE")
    public void buscarUsuarioSendoOMesmo_DeveRetornarUsuario() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/v1/usuarios/pedro.silva")
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    @Order(7)
    @WithMockUser(roles = "ADMIN")
    public void marcarAtraso_DeveTerSucesso() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.patch("/api/v1/usuarios/pedro.silva/atraso/marcar")
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNoContent());

    }

    @Test
    @Order(8)
    @WithMockUser(roles = "CLIENTE")
    public void marcarAtrasoSemSerAdmin_DeveRetornarException() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.patch("/api/v1/usuarios/pedro.silva/atraso/marcar")
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isForbidden());

    }
    @Test
    @Order(9)
    @WithMockUser(roles = "ADMIN")
    public void desmarcarAtrasoDeveTerSucesso() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.patch("/api/v1/usuarios/pedro.silva/atraso/desmarcar")
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNoContent());

    }

    @Test
    @Order(10)
    @WithMockUser(roles = "CLIENTE")
    public void desmarcarAtrasoSemSerAdmin_deveRetornarException() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.patch("/api/v1/usuarios/pedro.silva/atraso/desmarcar")
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isForbidden());

    }

    @Test
    @Order(11)
    @WithMockUser(username = "pedro.silva")
    public void atualizarUsuarioSendoOMesmo_DeveTerSucesso() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.put("/api/v1/usuarios/pedro.silva")
                                .content("""
                                        {
                                             
                                             "username": "pedro.santos",
                                             "password": "St@rG@z3r^#"
                                        }
                                        """)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNoContent());

    }
    @Test
    @Order(12)
    @WithMockUser(username = "kauan.silva")
    public void atualizarUsuarioNaoSendoOMesmo_deveRetornarException() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.put("/api/v1/usuarios/pedro.santos")
                                .content("""
                                        {
                                             
                                             "username": "carlos.romero",
                                             "password": "St@rG@z3r^#"
                                        }
                                        """)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isForbidden());

    }
    @Test
    @Order(13)
    @WithMockUser(username = "kauan.silva")
    public void desativarUsuarioNaoSendoOMesmo_deveRetornarException() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.delete("/api/v1/usuarios/pedro.santos")
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isForbidden());

    }


    @Test
    @Order(14)
    @WithMockUser(username = "pedro.santos")
    public void desativarUsuarioSendoOMesmo_deveTerSucesso() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.delete("/api/v1/usuarios/pedro.santos")
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNoContent());

    }




}
