package br.com.offer.app.domain.categoria;

import br.com.offer.app.domain.categoria.model.CategoriaId;
import br.com.offer.app.domain.sk.Descricao;
import br.com.offer.app.domain.categoria.usecase.RegistrarCategoriaUseCase.RegistrarCategoria;
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
class RegistrarCategoriaUseCaseIT {

    @Autowired
    private MockMvc mock;

    @Autowired
    private ObjectMapper mapper;

    @Test
    void caminhoFeliz() throws Exception {

        RegistrarCategoria command = RegistrarCategoria.builder()
            .descricao(Descricao.from("Eletrônicos"))
            .build();

        MvcResult result = mock.perform(post("/api/v1/categoria")
                .contentType(APPLICATION_JSON)
                .content(mapper.writeValueAsBytes(command)))
            .andExpect(status().isCreated())
            .andReturn();

        Assertions.assertNotNull(result);
    }

    @Test
    void deveRejeitarDescricaoDuplicada() throws Exception {

        // Primeiro registro
        RegistrarCategoria command = RegistrarCategoria.builder()
            .descricao(Descricao.from("Eletrônicos"))
            .build();

        mock.perform(post("/api/v1/categoria")
                .contentType(APPLICATION_JSON)
                .content(mapper.writeValueAsBytes(command)))
            .andExpect(status().isCreated());

        // Segundo registro com mesma descrição
        mock.perform(post("/api/v1/categoria")
                .contentType(APPLICATION_JSON)
                .content(mapper.writeValueAsBytes(command)))
            .andExpect(status().isBadRequest());
    }
}
