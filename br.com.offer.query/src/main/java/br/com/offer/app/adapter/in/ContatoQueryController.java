package br.com.offer.app.adapter.in;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.UUID;

import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.RequiredArgsConstructor;

import br.com.offer.app.usuario.app.ContatoAppService;
import br.com.offer.app.usuario.projection.Contato;

@RequiredArgsConstructor

@RestController
@RequestMapping(path = "/api/v1/contatos", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Contatos", description = "Consulta de contatos")
public class ContatoQueryController {

    private final ContatoAppService contatoAppService;

    @Operation(
        summary = "Listar contatos por usuário",
        description = "Lista todos os contatos de um usuário específico com suporte à paginação.",
        method = "GET",
        responses = {
            @ApiResponse(responseCode = "200", description = "Contatos listados com sucesso"),
        }
    )
    @GetMapping(path = "/usuario/{usuarioId}")
    @PageableAsQueryParam
    public Slice<Contato> listarContatosPorUsuario(
        @Parameter(description = "Id do usuário", required = true)
        @PathVariable UUID usuarioId,
        @Parameter(hidden = true) @PageableDefault(size = 20, sort = "contato") Pageable pageable) {
        return contatoAppService.findByUsuario(usuarioId, pageable);
    }

    @Operation(
        summary = "Buscar contato por ID",
        description = "Busca um contato específico pelo seu ID.",
        method = "GET",
        responses = {
            @ApiResponse(responseCode = "200", description = "Contato encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Contato não encontrado")
        }
    )
    @GetMapping(path = "/{id}")
    public Contato buscarContato(
        @Parameter(description = "Id do contato", required = true)
        @PathVariable UUID id) {
        return contatoAppService.getByID(id);
    }
}
