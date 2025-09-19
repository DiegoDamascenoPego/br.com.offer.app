package br.com.offer.app.domain.usuario.usecase;

import static lombok.AccessLevel.PRIVATE;

import java.util.Set;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Builder;
import lombok.Value;

import br.com.offer.app.domain.sk.Documento;
import br.com.offer.app.domain.sk.Nome;
import br.com.offer.app.domain.sk.TipoContato;
import br.com.offer.app.domain.usuario.model.Contato;
import br.com.offer.app.domain.usuario.model.Usuario;
import br.com.offer.app.domain.usuario.model.UsuarioId;

public interface RegistrarContatoUseCase {

    void handle(RegistrarContato command);

    void on(ContatoRegistrado event);

    @Value
    @Builder
    class RegistrarContato {

        @Valid
        @NotNull(message = "{Contato.pessoaId.NotNull}")
        @Schema(accessMode = Schema.AccessMode.READ_ONLY)
        UsuarioId usuarioId;

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

        UsuarioId id;

        Nome nome;

        Documento documento;

        Set<Contato> contatos;

        public static ContatoRegistrado from(Usuario usuario) {
            return ContatoRegistrado.builder()
                .id(usuario.getId())
                .nome(usuario.getNome())
                .documento(usuario.getDocumento())
                .contatos(usuario.getContatos())
                .build();
        }
    }
}
