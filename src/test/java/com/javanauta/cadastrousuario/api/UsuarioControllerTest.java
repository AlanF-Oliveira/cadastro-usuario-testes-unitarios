package com.javanauta.cadastrousuario.api;



import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javanauta.cadastrousuario.api.request.EnderecoRequestDTO;
import com.javanauta.cadastrousuario.api.request.EnderecoRequestDTOFixture;
import com.javanauta.cadastrousuario.api.request.UsuarioRequestDTO;
import com.javanauta.cadastrousuario.api.request.UsuarioRequestDTOFixture;
import com.javanauta.cadastrousuario.business.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@ExtendWith(MockitoExtension.class)
public class UsuarioControllerTest {
    @InjectMocks
    UsuarioController usuarioController;

    @Mock
    UsuarioService usuarioService;

    MockMvc mockMvc; //Responsável por fazer o servidor fake

    ObjectMapper objectMapper; //responsável por transformar o DTO em um JSON

    private String url;

    private UsuarioRequestDTO usuarioRequestDTO;

    private EnderecoRequestDTO enderecoRequestDTO;

    private String json;

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
        json = objectMapper.writeValueAsString(usuarioRequestDTO);

    }
}
