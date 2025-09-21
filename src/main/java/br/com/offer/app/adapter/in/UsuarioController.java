package br.com.offer.app.adapter.in;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.RequiredArgsConstructor;

import br.com.offer.app.domain.usuario.model.ContatoId;
import br.com.offer.app.domain.usuario.model.EnderecoId;
import br.com.offer.app.domain.usuario.model.UsuarioId;
import br.com.offer.app.domain.usuario.usecase.ListarEnderecosUseCase;
import br.com.offer.app.domain.usuario.usecase.ListarEnderecosUseCase.EnderecoResponse;
import br.com.offer.app.domain.usuario.usecase.ListarEnderecosUseCase.ListarEnderecos;
import br.com.offer.app.domain.usuario.usecase.RegistrarContatoUseCase;
import br.com.offer.app.domain.usuario.usecase.RegistrarContatoUseCase.RegistrarContato;
import br.com.offer.app.domain.usuario.usecase.RegistrarEnderecoUseCase;
import br.com.offer.app.domain.usuario.usecase.RegistrarEnderecoUseCase.RegistrarEndereco;
import br.com.offer.app.domain.usuario.usecase.RegistrarUsuarioUseCase;
import br.com.offer.app.domain.usuario.usecase.RegistrarUsuarioUseCase.RegistrarUsuario;

@RequiredArgsConstructor

@RestController
@RequestMapping(path = "/api/v1/usuarios", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Usuários", description = "Gerenciamento de usuários")
public class UsuarioController {

    private final RegistrarUsuarioUseCase registrarUsuarioUseCase;
    private final RegistrarContatoUseCase registrarContatoUseCase;
    private final RegistrarEnderecoUseCase registrarEnderecoUseCase;
    private final ListarEnderecosUseCase listarEnderecosUseCase;

    @Operation(
        summary = "Registrar Usuários da plataforma",
        description = "Registra um novo usuário na plataforma. Pode ser uma usuario física ou jurídica.",
        method = "POST",
        responses = {
            @ApiResponse(responseCode = "201", description = "Usuário registrado com sucesso"),
        }
    )
    @PostMapping
    public ResponseEntity<Void> registrarUsuario(@RequestBody RegistrarUsuario usuario) {
        UsuarioId id = registrarUsuarioUseCase.handle(usuario);

        return ResponseEntity.created(fromCurrentRequest()
                .path("/").path(id.asString()).build().toUri())
            .build();
    }

    @Operation(
        summary = "Registrar Informações de contato para um usuário da plataforma",
        description = "Registra uma nova informação de contato (e-mail, telefone, etc.) para um usuário existente na plataforma.",
        method = "POST",
        responses = {
            @ApiResponse(responseCode = "201", description = "Contato registrado com sucesso"),
        }
    )
    @PostMapping(path = "/{id}/contatos")
    public ResponseEntity<Void> registrarContato(
        @Parameter(description = "Id do usuário", required = true)
        @PathVariable UsuarioId id, @RequestBody RegistrarContato command) {

        final ContatoId contatoId = registrarContatoUseCase.handle(command.withUsuario(id));

        return ResponseEntity.created(fromCurrentRequest()
                .path("/").path(contatoId.asString()).build().toUri())
            .build();
    }

    @Operation(
        summary = "Registrar endereço para um usuário da plataforma",
        description = "Registra um novo endereço para um usuário existente na plataforma.",
        method = "POST",
        responses = {
            @ApiResponse(responseCode = "201", description = "Endereço registrado com sucesso"),
        }
    )
    @PostMapping(path = "/{id}/enderecos")
    public ResponseEntity<Void> registrarEndereco(
        @Parameter(description = "Id do usuário", required = true)
        @PathVariable UsuarioId id, @RequestBody RegistrarEndereco command) {
        final EnderecoId enderecoId = registrarEnderecoUseCase.handle(command.withUsuario(id));

        return ResponseEntity.created(fromCurrentRequest()
                .path("/").path(enderecoId.asString()).build().toUri())
            .build();
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
    public List<EnderecoResponse> listarEnderecos(
        @Parameter(description = "Id do usuário", required = true)
        @PathVariable UsuarioId id) {

        ListarEnderecos query = ListarEnderecos.builder()
            .usuarioId(id)
            .build();

        return listarEnderecosUseCase.handle(query);
    }
}
