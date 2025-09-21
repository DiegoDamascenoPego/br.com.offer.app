package br.com.offer.app.domain.usuario.usecase;

import static lombok.AccessLevel.PRIVATE;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Builder;
import lombok.Value;

import br.com.offer.app.domain.sk.Bairro;
import br.com.offer.app.domain.sk.Cep;
import br.com.offer.app.domain.sk.Cidade;
import br.com.offer.app.domain.sk.Complemento;
import br.com.offer.app.domain.sk.Estado;
import br.com.offer.app.domain.sk.Logradouro;
import br.com.offer.app.domain.sk.Numero;
import br.com.offer.app.domain.sk.Pais;
import br.com.offer.app.domain.usuario.model.Endereco;
import br.com.offer.app.domain.usuario.model.EnderecoId;
import br.com.offer.app.domain.usuario.model.UsuarioId;

public interface ListarEnderecosUseCase {

    List<EnderecoResponse> handle(ListarEnderecos query);

    @Value
    @Builder
    class ListarEnderecos {

        @Valid
        @NotNull(message = "{Endereco.usuarioId.NotNull}")
        @Schema(accessMode = Schema.AccessMode.READ_ONLY)
        UsuarioId usuarioId;
    }

    @Value
    @Builder(access = PRIVATE)
    class EnderecoResponse {

        EnderecoId id;

        Logradouro logradouro;

        Numero numero;

        Complemento complemento;

        Bairro bairro;

        Cidade cidade;

        Estado estado;

        Cep cep;

        Pais pais;

        public static EnderecoResponse from(Endereco endereco) {
            return EnderecoResponse.builder()
                .id(endereco.getId())
                .logradouro(endereco.getLogradouro())
                .numero(endereco.getNumero())
                .complemento(endereco.getComplemento())
                .bairro(endereco.getBairro())
                .cidade(endereco.getCidade())
                .estado(endereco.getEstado())
                .cep(endereco.getCep())
                .pais(endereco.getPais())
                .build();
        }
    }
}
