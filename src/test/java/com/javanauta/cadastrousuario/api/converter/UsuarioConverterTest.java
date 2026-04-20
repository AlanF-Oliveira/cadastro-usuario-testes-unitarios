package com.javanauta.cadastrousuario.api.converter;

import com.javanauta.cadastrousuario.api.request.EnderecoRequestDTO;
import com.javanauta.cadastrousuario.api.request.UsuarioRequestDTO;
import com.javanauta.cadastrousuario.api.request.UsuarioRequestDTOFixture;
import com.javanauta.cadastrousuario.infrastructure.entities.EnderecoEntity;
import com.javanauta.cadastrousuario.infrastructure.entities.UsuarioEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
public class UsuarioConverterTest {

    @InjectMocks
    UsuarioConverter usuarioConverter;

    @Mock
    Clock clock;
    UsuarioEntity usuarioEntity;
    EnderecoEntity enderecoEntity;
    UsuarioRequestDTO usuarioRequestDTO;
    EnderecoRequestDTO enderecoRequestDTO;
    LocalDateTime dataHora;

    @BeforeEach
    public void setup() {
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
                .nome("Usuario")
                .documento("2345674")
                .email("alanf@gmail.com")
                .dataCadastro(dataHora)
                .endereco(enderecoEntity)
                .build();
        enderecoRequestDTO = EnderecoRequestDTO.builder()
                .rua("Rua Spring Boot").numero(23L).bairro("Bairro Java").complemento("Apt 5").cidade("Javaland").cep("60456543").build();
        usuarioRequestDTO = UsuarioRequestDTOFixture.build("Usuario", "alanf@gmail.com", "2345674", enderecoRequestDTO);
        ZoneId zoneId = ZoneId.systemDefault();
        Clock fixedClock = Clock.fixed(dataHora.atZone(zoneId).toInstant(), zoneId);
        doReturn(fixedClock.instant()).when(clock).instant();
        doReturn(fixedClock.getZone()).when(clock).getZone();
    }

    @Test
    void deveCorverterParaUsuarioEntity() {
        UsuarioEntity entity = usuarioConverter.paraUsuarioEntity(usuarioRequestDTO);
        assertEquals(usuarioEntity, entity);

    }
}
