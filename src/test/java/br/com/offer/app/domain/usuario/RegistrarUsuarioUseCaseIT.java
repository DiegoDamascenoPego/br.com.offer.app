package br.com.offer.app.domain.usuario;

import br.com.offer.app.domain.usuario.model.UsuarioId;
import br.com.offer.app.domain.sk.Nome;
import br.com.offer.app.domain.sk.Documento;
import br.com.offer.app.domain.usuario.model.TipoUsuario;
import br.com.offer.app.domain.usuario.usecase.RegistrarUsuarioUseCase.RegistrarUsuario;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureWebMvc
class RegistrarUsuarioUseCaseIT {

    @Autowired
    private MockMvc mock;

    @Autowired
    private ObjectMapper mapper;

    @Test
    void caminhoFeliz() throws Exception {

        RegistrarUsuario command = RegistrarUsuario.builder()
            .nome(Nome.from("João Silva"))
            .documento(Documento.from("12345678901"))
            .tipo(TipoUsuario.CLIENTE)
            .build();

        MvcResult result = mock.perform(post("/api/v1/usuarios")
                .contentType(APPLICATION_JSON)
                .content(mapper.writeValueAsBytes(command)))
            .andExpect(status().isCreated())
            .andExpect(header().exists("Location"))
            .andExpect(redirectedUrlPattern("**/api/v1/usuarios/*"))
            .andReturn();

        Assertions.assertNotNull(result);
    }

    @Test
    void deveRejeitarDocumentoDuplicado() throws Exception {

        // Primeiro registro
        RegistrarUsuario command = RegistrarUsuario.builder()
            .nome(Nome.from("Maria Santos"))
            .documento(Documento.from("98765432100"))
            .tipo(TipoUsuario.CLIENTE)
            .build();

        mock.perform(post("/api/v1/usuarios")
                .contentType(APPLICATION_JSON)
                .content(mapper.writeValueAsBytes(command)))
            .andExpect(status().isCreated());

        // Segundo registro com mesmo documento
        RegistrarUsuario commandDuplicado = RegistrarUsuario.builder()
            .nome(Nome.from("Pedro Oliveira"))
            .documento(Documento.from("98765432100")) // Mesmo documento
            .tipo(TipoUsuario.CLIENTE)
            .build();

        mock.perform(post("/api/v1/usuarios")
                .contentType(APPLICATION_JSON)
                .content(mapper.writeValueAsBytes(commandDuplicado)))
            .andExpect(status().isBadRequest());
    }
}
