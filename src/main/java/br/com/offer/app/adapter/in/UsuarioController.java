package br.com.offer.app.adapter.in;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.RequiredArgsConstructor;

import br.com.offer.app.domain.usuario.model.UsuarioId;
import br.com.offer.app.domain.usuario.usecase.RegistrarContatoUseCase;
import br.com.offer.app.domain.usuario.usecase.RegistrarContatoUseCase.RegistrarContato;
import br.com.offer.app.domain.usuario.usecase.RegistrarUsuarioUseCase;
import br.com.offer.app.domain.usuario.usecase.RegistrarUsuarioUseCase.RegistrarUsuario;

@RequiredArgsConstructor

@RestController
@RequestMapping(path = "/api/v1/usuarios", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Usuários", description = "Gerenciamento de usuários")
public class UsuarioController {

    private final RegistrarUsuarioUseCase registrarUsuarioUseCase;
    private final RegistrarContatoUseCase registrarContatoUseCase;

    @Operation(
        summary = "Registrar Usuários da plataforma",
        description = "Registra um novo usuário na plataforma. Pode ser uma pessoa física ou jurídica.",
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
        method = "PUT",
        responses = {
            @ApiResponse(responseCode = "200", description = "Contato registrado com sucesso"),
        }
    )

    @PutMapping(path = "/{id}/contatos")
    public void registrarContato(
        @Parameter(description = "Id do usuário", required = true)
        @PathVariable UsuarioId id, @RequestBody RegistrarContato contato) {
        RegistrarContato command = RegistrarContato.builder()
            .usuarioId(id)
            .tipo(contato.getTipo())
            .contato(contato.getContato())
            .build();

        registrarContatoUseCase.handle(command);
    }
}
