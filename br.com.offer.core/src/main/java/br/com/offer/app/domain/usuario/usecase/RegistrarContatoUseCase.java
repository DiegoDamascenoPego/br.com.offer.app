package br.com.offer.app.domain.usuario.usecase;

import static lombok.AccessLevel.PRIVATE;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Builder;
import lombok.Value;
import lombok.With;

import br.com.offer.app.domain.sk.TipoContato;
import br.com.offer.app.domain.usuario.model.ContatoId;
import br.com.offer.app.domain.usuario.model.UsuarioId;

public interface RegistrarContatoUseCase {

    ContatoId handle(RegistrarContato command);

    void on(ContatoRegistrado event);

    @Value
    @Builder
    class RegistrarContato {

        @With
        @Schema(accessMode = Schema.AccessMode.READ_ONLY)
        UsuarioId usuario;

        @Valid
        @NotNull(message = "{Contato.tipo.NotNull}")
        @Schema(description = "Tipo do Contato", allowableValues = { "EMAIL", "TELEFONE" })
        TipoContato tipo;

        @NotBlank(message = "{ValorContato.NotBlank}")
        @Size(max = 256, message = "{ValorContato.Size}")
        @Schema(description = "Contato do usuário", example = "teste@teste.com.br", type = "string", maxLength = 256)
        String contato;
    }

    @Value
    @Builder(access = PRIVATE)
    class ContatoRegistrado {

        ContatoId contatoId;
        UsuarioId usuarioId;
        TipoContato tipo;
        String valor;

        public static ContatoRegistrado from(ContatoId contatoId, UsuarioId usuarioId, TipoContato tipo, String valor) {
            return ContatoRegistrado.builder()
                .contatoId(contatoId)
                .usuarioId(usuarioId)
                .tipo(tipo)
                .valor(valor)
                .build();
        }
    }
}
