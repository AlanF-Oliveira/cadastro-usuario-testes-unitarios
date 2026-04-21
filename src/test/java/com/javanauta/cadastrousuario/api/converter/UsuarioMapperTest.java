package com.javanauta.cadastrousuario.api.converter;

import com.javanauta.cadastrousuario.api.response.EnderecoResponseDTO;
import com.javanauta.cadastrousuario.api.response.EnderecoResponseDTOFixture;
import com.javanauta.cadastrousuario.api.response.UsuarioResponseDTO;
import com.javanauta.cadastrousuario.api.response.UsuarioResponseDTOFixture;
import com.javanauta.cadastrousuario.infrastructure.entities.EnderecoEntity;
import com.javanauta.cadastrousuario.infrastructure.entities.UsuarioEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UsuarioMapperTest {


    UsuarioMapper usuarioMapper;
    UsuarioEntity usuarioEntity;
    EnderecoEntity enderecoEntity;
    UsuarioResponseDTO usuarioResponseDTO;
    EnderecoResponseDTO enderecoResponseDTO;
    LocalDateTime dataHora;

    @BeforeEach
    public void setup() {
        usuarioMapper = Mappers.getMapper(UsuarioMapper.class);
        dataHora = LocalDateTime.of(2026, 4, 20, 17, 55, 22);
        enderecoEntity = EnderecoEntity.builder()
                .rua("Rua Spring Boot")
                .bairro("Bairro Java")
                .cep("60456543")
                .cidade("Javaland")
                .numero(23L)
                .complemento("Apt 5")
                .build();
        usuarioEntity = UsuarioEntity.builder()
                .id(123L)
                .nome("Usuario")
                .documento("2345674")
                .email("alanf@gmail.com")
                .dataCadastro(dataHora)
                .endereco(enderecoEntity)
                .build();
        enderecoResponseDTO = EnderecoResponseDTOFixture.build(
                "Rua Spring Boot",
                23L,
                "Bairro Java",
                "Apt 5",
                "Javaland",
                "60456543"
        );
        usuarioResponseDTO = UsuarioResponseDTOFixture.build(
                123L,
                "Usuario",
                "alanf@gmail.com",
                "2345674",
                enderecoResponseDTO
        );
    }

    @Test
    void deveConverterParaUsuarioResponseDTO() {
        UsuarioResponseDTO dto = usuarioMapper.paraUsuarioResponseDTO(usuarioEntity);
        assertEquals(usuarioResponseDTO, dto);

    }
}
