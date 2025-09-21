package br.com.offer.app.usuario.adapter.in;

import static br.com.offer.app.usuario.usecase.ListarEnderecosUseCase.ListagemEndereco;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.RequiredArgsConstructor;

import br.com.offer.app.usuario.usecase.ListarEnderecosUseCase;

@RequiredArgsConstructor

@RestController
@RequestMapping(path = "/api/v1/usuarios", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Usuários", description = "Gerenciamento de usuários")
public class UsuarioQueryController {

    private final ListarEnderecosUseCase listarEnderecosUseCase;

    @Operation(
        summary = "Listar endereços de um usuário",
        description = "Lista todos os endereços ativos de um usuário da plataforma.",
        method = "GET",
        responses = {
            @ApiResponse(responseCode = "200", description = "Endereços listados com sucesso"),
        }
    )
    @GetMapping(path = "/{id}/enderecos")
    public List<ListagemEndereco> listarEnderecos(
        @Parameter(description = "Id do usuário", required = true)
        @PathVariable UUID id) {

        return listarEnderecosUseCase.handle(id);
    }
}
