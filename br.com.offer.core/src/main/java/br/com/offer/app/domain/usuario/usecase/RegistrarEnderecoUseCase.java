package br.com.offer.app.domain.usuario.usecase;

import static lombok.AccessLevel.PRIVATE;

import jakarta.validation.Valid;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Builder;
import lombok.Value;
import lombok.With;

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

public interface RegistrarEnderecoUseCase {

    EnderecoId handle(RegistrarEndereco command);

    void on(EnderecoRegistrado event);

    @Value
    @Builder
    class RegistrarEndereco {

        @With
        @Schema(accessMode = Schema.AccessMode.READ_ONLY)
        UsuarioId usuario;

        @Valid
        Logradouro logradouro;

        @Valid
        Numero numero;

        @Valid
        Complemento complemento;

        @Valid
        Bairro bairro;

        @Valid
        Cidade cidade;

        @Valid
        Estado estado;

        @Valid
        Cep cep;

        @Valid
        Pais pais;
    }

    @Value
    @Builder(access = PRIVATE)
    class EnderecoRegistrado {

        EnderecoId id;

        UsuarioId usuario;

        Logradouro logradouro;

        Numero numero;

        Complemento complemento;

        Bairro bairro;

        Cidade cidade;

        Estado estado;

        Cep cep;

        Pais pais;

        public static EnderecoRegistrado from(Endereco endereco) {
            return EnderecoRegistrado.builder()
                .id(endereco.getId())
                .usuario(endereco.getUsuario())
                .logradouro(endereco.getLogradouro())
                .numero(endereco.getNumero())
                .complemento(endereco.getComplemento())
                .bairro(endereco.getBairro())
                .cidade(endereco.getCidade())
                .build();
        }
    }
}
