package com.javanauta.cadastrousuario.api;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javanauta.cadastrousuario.api.request.EnderecoRequestDTO;
import com.javanauta.cadastrousuario.api.request.EnderecoRequestDTOFixture;
import com.javanauta.cadastrousuario.api.request.UsuarioRequestDTO;
import com.javanauta.cadastrousuario.api.request.UsuarioRequestDTOFixture;
import com.javanauta.cadastrousuario.api.response.EnderecoResponseDTO;
import com.javanauta.cadastrousuario.api.response.EnderecoResponseDTOFixture;
import com.javanauta.cadastrousuario.api.response.UsuarioResponseDTO;
import com.javanauta.cadastrousuario.api.response.UsuarioResponseDTOFixture;
import com.javanauta.cadastrousuario.business.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class UsuarioControllerTest {
    @InjectMocks
    UsuarioController usuarioController;

    @Mock
    UsuarioService usuarioService;

    MockMvc mockMvc; //Responsável por fazer o servidor fake

    private final ObjectMapper objectMapper = new ObjectMapper(); //responsável por transformar o DTO em um JSON

    private String url;

    private UsuarioRequestDTO usuarioRequestDTO;

    private EnderecoRequestDTO enderecoRequestDTO;

    private String json;

    private EnderecoResponseDTO enderecoResponseDTO;

    private UsuarioResponseDTO usuarioResponseDTO;

    @BeforeEach
    void setup() throws JsonProcessingException {
        mockMvc = MockMvcBuilders.standaloneSetup(usuarioController).alwaysDo(print()).build();
        url = "/user";
        enderecoRequestDTO = EnderecoRequestDTOFixture.build(
                "Rua Spring Boot",
                23L,
                "Bairro Java",
                "Apt 5",
                "Javaland",
                "60456543"
        );
        usuarioRequestDTO = UsuarioRequestDTOFixture.build(
                "Usuario",
                "alanf@gmail.com",
                "2345674",
                enderecoRequestDTO
        );
        enderecoResponseDTO = EnderecoResponseDTOFixture.build(
                "Rua Spring Boot",
                23L,
                "Bairro Java",
                "Apt 5",
                "Javaland",
                "60456543"
        );
        usuarioResponseDTO = UsuarioResponseDTOFixture.build(
                1452L,
                "Usuario",
                "alanf@gmail.com",
                "2345674",
                enderecoResponseDTO
        );
        json = objectMapper.writeValueAsString(usuarioRequestDTO);
    }

    @Test
    void deveGravarDadosDeUsuarioComSucesso() throws Exception {
        when(usuarioService.gravarUsuarios(usuarioRequestDTO)).thenReturn(usuarioResponseDTO);

        mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json)
        ).andExpect(status().isOk());

        verify(usuarioService).gravarUsuarios(usuarioRequestDTO);
        verifyNoMoreInteractions(usuarioService);
    }

    @Test
    void naoDeveGravarDadosDeUsuarioCasoJsonNull() throws Exception {
        mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());
        verifyNoInteractions(usuarioService);
    }

    @Test
    void deveAtualizarDadosDeUsuarioComSucesso() throws Exception {
        when(usuarioService.atualizaCadastro(usuarioRequestDTO)).thenReturn(usuarioResponseDTO);

        mockMvc.perform(put(url)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json)
        ).andExpect(status().isOk());

        verify(usuarioService).atualizaCadastro(usuarioRequestDTO);
        verifyNoMoreInteractions(usuarioService);
    }

    @Test
    void naoDeveAtualizarDadosDeUsuarioCasoJsonNull() throws Exception {
        mockMvc.perform(put(url)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());
        verifyNoInteractions(usuarioService);
    }

    @Test
    void deveBuscarDadosDeUsuariosComSucesso() throws Exception {

        mockMvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .param("email", "alanf@gmail.com")
                .content(json)
        ).andExpect(status().isOk());
        verify(usuarioService).buscaDadosUsuario("alanf@gmail.com");
        verifyNoMoreInteractions(usuarioService);

    }
}
