package br.com.offer.app.domain.usuario;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.offer.app.domain.sk.Bairro;
import br.com.offer.app.domain.sk.Cep;
import br.com.offer.app.domain.sk.Cidade;
import br.com.offer.app.domain.sk.Complemento;
import br.com.offer.app.domain.sk.Documento;
import br.com.offer.app.domain.sk.Estado;
import br.com.offer.app.domain.sk.Logradouro;
import br.com.offer.app.domain.sk.Nome;
import br.com.offer.app.domain.sk.Numero;
import br.com.offer.app.domain.sk.Pais;
import br.com.offer.app.domain.usuario.model.TipoUsuario;
import br.com.offer.app.domain.usuario.model.UsuarioId;
import br.com.offer.app.domain.usuario.usecase.RegistrarEnderecoUseCase.RegistrarEndereco;
import br.com.offer.app.domain.usuario.usecase.RegistrarUsuarioUseCase.RegistrarUsuario;

@SpringBootTest
@AutoConfigureWebMvc
class RegistrarEnderecoUseCaseIT {

    @Autowired
    private MockMvc mock;

    @Autowired
    private ObjectMapper mapper;

    @Test
    void caminhoFeliz() throws Exception {
        // Primeiro, criar um usuário para associar o endereço
        RegistrarUsuario usuarioCommand = RegistrarUsuario.builder()
            .nome(Nome.from("João Silva"))
            .documento(Documento.from("12345678901"))
            .tipo(TipoUsuario.CLIENTE)
            .build();

        MvcResult usuarioResult = mock.perform(post("/api/v1/usuarios")
                .contentType(APPLICATION_JSON)
                .content(mapper.writeValueAsBytes(usuarioCommand)))
            .andExpect(status().isCreated())
            .andReturn();

        String location = usuarioResult.getResponse().getHeader("Location");
        String usuarioIdStr = location.substring(location.lastIndexOf("/") + 1);
        UsuarioId usuarioId = UsuarioId.from(usuarioIdStr);

        // Agora registrar o endereço
        RegistrarEndereco enderecoCommand = RegistrarEndereco.builder()
            .usuario(usuarioId)
            .logradouro(Logradouro.from("Rua das Flores"))
            .numero(Numero.from("123"))
            .complemento(Complemento.from("Apto 45"))
            .bairro(Bairro.from("Centro"))
            .cidade(Cidade.from("São Paulo"))
            .estado(Estado.from("SP"))
            .cep(Cep.from("01234567"))
            .pais(Pais.from("Brasil"))
            .build();

        MvcResult enderecoResult = mock.perform(post("/api/v1/usuarios/" + usuarioIdStr + "/enderecos")
                .contentType(APPLICATION_JSON)
                .content(mapper.writeValueAsBytes(enderecoCommand)))
            .andExpect(status().isCreated())
            .andExpect(header().exists("Location"))
            .andExpect(redirectedUrlPattern("**/api/v1/usuarios/" + usuarioIdStr + "/enderecos/*"))
            .andReturn();

        Assertions.assertNotNull(enderecoResult);
        Assertions.assertNotNull(enderecoResult.getResponse().getHeader("Location"));
    }

    @Test
    void deveValidarCamposObrigatorios() throws Exception {
        // Criar um usuário primeiro
        RegistrarUsuario usuarioCommand = RegistrarUsuario.builder()
            .nome(Nome.from("Maria Silva"))
            .documento(Documento.from("98765432109"))
            .tipo(TipoUsuario.CLIENTE)
            .build();

        MvcResult usuarioResult = mock.perform(post("/api/v1/usuarios")
                .contentType(APPLICATION_JSON)
                .content(mapper.writeValueAsBytes(usuarioCommand)))
            .andExpect(status().isCreated())
            .andReturn();

        String location = usuarioResult.getResponse().getHeader("Location");
        String usuarioIdStr = location.substring(location.lastIndexOf("/") + 1);


        RegistrarEndereco enderecoCommand = RegistrarEndereco.builder()
            .usuario(UsuarioId.from(usuarioIdStr))
            .logradouro(Logradouro.from("Rua das Flores"))
            .build();

        mock.perform(post("/api/v1/usuarios/" + usuarioIdStr + "/enderecos")
                .contentType(APPLICATION_JSON)
                .content(mapper.writeValueAsBytes(enderecoCommand)))
            .andExpect(status().isCreated());
    }
}
