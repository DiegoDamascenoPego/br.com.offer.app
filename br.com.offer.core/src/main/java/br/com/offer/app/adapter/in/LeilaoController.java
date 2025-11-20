package br.com.offer.app.adapter.in;

import static br.com.offer.app.domain.leilao.usecase.RegistrarLeilaoObjetoUseCase.RegistrarLeilaoObjeto;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.RequiredArgsConstructor;

import br.com.offer.app.domain.leilao.model.LeilaoId;
import br.com.offer.app.domain.leilao.usecase.RegistrarLeilaoObjetoUseCase;
import br.com.offer.app.domain.leilao.usecase.RegistrarLeilaoUseCase;
import br.com.offer.app.domain.leilao.usecase.RegistrarLeilaoUseCase.RegistrarLeilao;

@RequiredArgsConstructor

@RestController
@RequestMapping(path = "/api/v1/leiloes", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Leilões", description = "Gerenciamento de leilões")
public class LeilaoController {

    private final RegistrarLeilaoUseCase registrarLeilaoUseCase;
    private final RegistrarLeilaoObjetoUseCase registrarLeilaoObjetoUseCase;

    @Operation(
        summary = "Registrar leilão",
        description = "Registra um novo leilão na plataforma com todas as informações necessárias para o processo de licitação.",
        method = "POST",
        responses = {
            @ApiResponse(responseCode = "201", description = "Leilão registrado com sucesso")
        }
    )
    @PostMapping
    public ResponseEntity<Void> registrarLeilao(@RequestBody RegistrarLeilao leilao) {
        LeilaoId id = registrarLeilaoUseCase.handle(leilao);

        return ResponseEntity.created(fromCurrentRequest()
                .path("/").path(id.getId().toString()).build().toUri())
            .build();
    }

    @Operation(
        summary = "Registrar leilão",
        description = "Registra um novo objeto para um leilão existente na plataforma, adicionando itens que serão leiloados durante o evento.",
        method = "PUT",
        responses = {
            @ApiResponse(responseCode = "200", description = "Objeto do leilão registrado com sucesso")
        }
    )
    @PutMapping(path = "/{id}/objetos")
    public ResponseEntity<Void> registrarObjeto(
        @PathVariable LeilaoId id,
        @RequestBody RegistrarLeilaoObjeto command) {
        registrarLeilaoObjetoUseCase.handle(command.withId(id));

        return ResponseEntity.ok()
            .header(fromCurrentRequest().toString())
            .build();
    }
}
