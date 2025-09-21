package br.com.offer.app.usuario.usecase;

import static lombok.AccessLevel.PRIVATE;

import java.util.List;
import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Builder;
import lombok.Value;
import lombok.With;

import br.com.offer.app.usuario.domain.EnderecoQuery;

public interface ListarEnderecosUseCase {

    List<ListagemEndereco> handle(UUID usuario);

    @Value
    @Builder(access = PRIVATE)
    class ListagemEndereco {

        UUID id;

        UUID usuario;

        String logradouro;

        String numero;

        String complemento;

        String bairro;

        String cidade;

        String estado;

        String cep;

        String pais;

        public static ListagemEndereco from(EnderecoQuery endereco) {
            return ListagemEndereco.builder()
                .id(endereco.getId())
                .usuario(endereco.getUsuario())
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
