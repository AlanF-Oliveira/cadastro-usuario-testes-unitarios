package com.javanauta.cadastrousuario.business;

import com.javanauta.cadastrousuario.api.converter.UsuarioConverter;
import com.javanauta.cadastrousuario.api.converter.UsuarioMapper;
import com.javanauta.cadastrousuario.api.converter.UsuarioUpdateMapper;
import com.javanauta.cadastrousuario.api.request.EnderecoRequestDTO;
import com.javanauta.cadastrousuario.api.request.EnderecoRequestDTOFixture;
import com.javanauta.cadastrousuario.api.request.UsuarioRequestDTO;
import com.javanauta.cadastrousuario.api.request.UsuarioRequestDTOFixture;
import com.javanauta.cadastrousuario.api.response.EnderecoResponseDTO;
import com.javanauta.cadastrousuario.api.response.EnderecoResponseDTOFixture;
import com.javanauta.cadastrousuario.api.response.UsuarioResponseDTO;
import com.javanauta.cadastrousuario.api.response.UsuarioResponseDTOFixture;
import com.javanauta.cadastrousuario.infrastructure.entities.EnderecoEntity;
import com.javanauta.cadastrousuario.infrastructure.entities.UsuarioEntity;
import com.javanauta.cadastrousuario.infrastructure.exceptions.BusinessException;
import com.javanauta.cadastrousuario.infrastructure.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {
    @InjectMocks
    UsuarioService usuarioService;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    UsuarioConverter usuarioConverter;

    @Mock
    UsuarioUpdateMapper usuarioUpdateMapper;

    @Mock
    UsuarioMapper usuarioMapper;

    UsuarioEntity usuarioEntity;
    EnderecoEntity enderecoEntity;
    UsuarioRequestDTO usuarioRequestDTO;
    EnderecoRequestDTO enderecoRequestDTO;
    UsuarioResponseDTO usuarioResponseDTO;
    EnderecoResponseDTO enderecoResponseDTO;
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


    }

    @Test
    public void testLoginSucess() {

    }

    @Test
    void deveSalvarUsuarioComSucesso() {
        when(usuarioRepository.saveAndFlush(usuarioEntity)).thenReturn(usuarioEntity);
        UsuarioEntity entity = usuarioService.salvaUsuario(usuarioEntity);
        assertEquals(entity, usuarioEntity);
        verify(usuarioRepository).saveAndFlush(usuarioEntity);
        verifyNoMoreInteractions(usuarioRepository);
    }

    @Test
    void deveGravarUsuarioComSucesso(){
        when(usuarioConverter.paraUsuarioEntity(usuarioRequestDTO)).thenReturn(usuarioEntity);
        when(usuarioRepository.saveAndFlush(usuarioEntity)).thenReturn(usuarioEntity);
        when(usuarioMapper.paraUsuarioResponseDTO(usuarioEntity)).thenReturn(usuarioResponseDTO);
        UsuarioResponseDTO dto = usuarioService.gravarUsuarios(usuarioRequestDTO);
        assertEquals(dto, usuarioResponseDTO);
        verify(usuarioConverter).paraUsuarioEntity(usuarioRequestDTO);
        verify(usuarioRepository).saveAndFlush(usuarioEntity);
        verify(usuarioMapper).paraUsuarioResponseDTO(usuarioEntity);
        verifyNoMoreInteractions(usuarioRepository, usuarioConverter, usuarioMapper);
    }

    @Test
    void naoDeveSalvarUsuariosCasoUsuarioRequestDTONull(){
        BusinessException e = assertThrows(BusinessException.class,
                () -> usuarioService.gravarUsuarios(null));
        assertThat(e, notNullValue());
        assertThat(e.getMessage(), is("Erro ao gravar dados de usuário"));
        assertThat(e.getCause(), notNullValue());
        assertThat(e.getCause().getMessage(), is("Os dados do usuário são obrigatórios"));
        verifyNoInteractions(usuarioMapper, usuarioConverter, usuarioRepository);
    }


}
