package br.com.offer.app.adapter.in;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.RequiredArgsConstructor;

import br.com.offer.app.domain.leilao.model.LeilaoId;
import br.com.offer.app.domain.leilao.usecase.RegistrarLeilaoUseCase;
import br.com.offer.app.domain.leilao.usecase.RegistrarLeilaoUseCase.RegistrarLeilao;

@RequiredArgsConstructor

@RestController
@RequestMapping(path = "/api/v1/leiloes", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Leilões", description = "Gerenciamento de leilões")
public class LeilaoController {

    private final RegistrarLeilaoUseCase registrarLeilaoUseCase;

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
}
