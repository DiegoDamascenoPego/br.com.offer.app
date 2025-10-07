package br.com.offer.app.adapter.in;

import static br.com.offer.app.usuario.usecase.ListarEnderecosUseCase.ListagemEndereco;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.List;
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
import br.com.offer.app.usuario.app.UsuarioAppService;
import br.com.offer.app.usuario.projection.Contato;
import br.com.offer.app.usuario.projection.Usuario;
import br.com.offer.app.usuario.usecase.ListarEnderecosUseCase;

@RequiredArgsConstructor

@RestController
@RequestMapping(path = "/api/v1/usuarios", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Usuários", description = "Gerenciamento de usuários")
public class UsuarioQueryController {

    private final UsuarioAppService usuarioAppService;
    private final ContatoAppService contatoAppService;
    private final ListarEnderecosUseCase listarEnderecosUseCase;

    @Operation(
        summary = "Listar usuários",
        description = "Lista todos os usuários da plataforma com suporte à paginação.",
        method = "GET",
        responses = {
            @ApiResponse(responseCode = "200", description = "Usuários listados com sucesso"),
        }
    )
    @GetMapping
    @PageableAsQueryParam
    public Slice<Usuario> listarUsuarios(
        @Parameter(hidden = true) @PageableDefault(size = 20, sort = "nome") Pageable pageable) {
        return usuarioAppService.findAll(pageable);
    }

    @Operation(
        summary = "Buscar usuário por ID",
        description = "Busca um usuário específico pelo seu ID.",
        method = "GET",
        responses = {
            @ApiResponse(responseCode = "200", description = "Usuário encontrado com sucesso"),
        }
    )
    @GetMapping(path = "/{id}")
    public Usuario buscarUsuario(@PathVariable UUID id) {
        return usuarioAppService.getByID(id);
    }

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

    @Operation(
        summary = "Listar contatos de um usuário",
        description = "Lista todos os contatos de um usuário da plataforma com suporte à paginação.",
        method = "GET",
        responses = {
            @ApiResponse(responseCode = "200", description = "Contatos listados com sucesso"),
        }
    )
    @GetMapping(path = "/{id}/contatos")
    @PageableAsQueryParam
    public Slice<Contato> listarContatos(
        @Parameter(description = "Id do usuário", required = true)
        @PathVariable UUID id,
        @Parameter(hidden = true) @PageableDefault(size = 20, sort = "contato") Pageable pageable) {
        return contatoAppService.findByUsuario(id, pageable);
    }
}
