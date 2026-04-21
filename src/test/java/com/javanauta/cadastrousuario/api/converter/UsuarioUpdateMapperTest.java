package com.javanauta.cadastrousuario.api.converter;

import com.javanauta.cadastrousuario.api.request.EnderecoRequestDTO;
import com.javanauta.cadastrousuario.api.request.EnderecoRequestDTOFixture;
import com.javanauta.cadastrousuario.api.request.UsuarioRequestDTO;
import com.javanauta.cadastrousuario.api.request.UsuarioRequestDTOFixture;
import com.javanauta.cadastrousuario.infrastructure.entities.EnderecoEntity;
import com.javanauta.cadastrousuario.infrastructure.entities.UsuarioEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UsuarioUpdateMapperTest {
    UsuarioUpdateMapper usuarioMapper;
    UsuarioEntity usuarioEntityEsperado;
    UsuarioEntity usuarioEntity;
    EnderecoEntity enderecoEntity;
    UsuarioRequestDTO usuarioRequestDTO;
    EnderecoRequestDTO enderecoRequestDTO;
    LocalDateTime dataHora;

    @BeforeEach
    public void setup() {
        usuarioMapper = Mappers.getMapper(UsuarioUpdateMapper.class);
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
        enderecoRequestDTO = EnderecoRequestDTOFixture.build(
                "Rua Spring Boot",
                23L,
                "Bairro Java",
                "Apt 5",
                "Javaland",
                "60456543"
        );
        usuarioRequestDTO = UsuarioRequestDTOFixture.build(
                "Usuario Teste",
                null,
                "2345674000",
                enderecoRequestDTO
        );

        usuarioEntityEsperado = UsuarioEntity.builder()
                .id(123L)
                .nome("Usuario Teste")
                .email("alanf@gmail.com")
                .documento("2345674000")
                .dataCadastro(dataHora)
                .endereco(enderecoEntity)
                .build();
    }

    @Test
    void deveConverterParaUsuarioEntity() {
        UsuarioEntity entity = usuarioMapper.updateUsuarioFromDTO(usuarioRequestDTO, usuarioEntity);
        assertEquals(usuarioEntityEsperado, entity);

    }
}
