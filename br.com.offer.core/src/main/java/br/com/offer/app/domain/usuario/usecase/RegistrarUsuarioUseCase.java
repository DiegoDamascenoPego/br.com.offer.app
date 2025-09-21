package br.com.offer.app.domain.usuario.usecase;

import static lombok.AccessLevel.PRIVATE;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Builder;
import lombok.Value;

import br.com.offer.app.domain.sk.Documento;
import br.com.offer.app.domain.sk.Nome;
import br.com.offer.app.domain.usuario.model.TipoUsuario;
import br.com.offer.app.domain.usuario.model.Usuario;
import br.com.offer.app.domain.usuario.model.UsuarioId;

public interface RegistrarUsuarioUseCase {

    UsuarioId handle(RegistrarUsuario command);

    void on(UsuarioRegistrado event);

    @Value
    @Builder
    class RegistrarUsuario {

        @Valid
        @NotNull(message = "{Usuario.nome.NotNull}")
        @Schema(description = "Nome do usuário", example = "Louro José", type = "string", maxLength = 256)
        Nome nome;

        @Valid
        @NotNull(message = "{Usuario.documento.NotNull}")
        @Schema(description = "Documento do usuário", example = "CPF:47202497018", type = "string", maxLength = 128)
        Documento documento;

        @Valid
        @NotNull(message = "{Usuario.tipo.NotNull}")
        @Schema(description = "Tipo Usuário", example = "CLIENTE")
        TipoUsuario tipo;
    }

    @Value
    @Builder(access = PRIVATE)
    class UsuarioRegistrado {

        UsuarioId id;
        Nome nome;
        Documento documento;
        TipoUsuario tipo;

        public static UsuarioRegistrado from(Usuario usuario) {
            return UsuarioRegistrado.builder()
                .id(usuario.getId())
                .nome(usuario.getNome())
                .documento(usuario.getDocumento())
                .tipo(usuario.getTipo())
                .build();
        }
    }
}
