package br.com.offer.app.domain.leilao.usecase;

import static lombok.AccessLevel.PRIVATE;

import java.util.Set;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Builder;
import lombok.Value;
import lombok.With;

import br.com.offer.app.domain.categoria.model.CategoriaId;
import br.com.offer.app.domain.leilao.model.LeilaoId;
import br.com.offer.app.domain.leilao.model.LeilaoObjeto;
import br.com.offer.app.domain.leilao.model.ObjetoId;
import br.com.offer.app.domain.sk.Descricao;

public interface RegistrarLeilaoObjetoUseCase {

    void handle(RegistrarLeilaoObjeto command);

    void on(LeilaoObjetoRegistrado event);

    @Value
    @Builder
    class RegistrarLeilaoObjeto {

        @With
        @Schema(accessMode = Schema.AccessMode.READ_ONLY)
        LeilaoId id;

        Set<RegistrarObjeto> items;
    }

    record RegistrarObjeto(ObjetoId id,
                           CategoriaId categoria,
                           @Valid
                           @NotNull(message = "{Leilao.descricao.NotNull}")
                           Descricao descricao) {

    }

    @Value
    @Builder(access = PRIVATE)
    class LeilaoObjetoRegistrado {

        LeilaoId id;
        Set<ObjetoRegistrado> objetos;

        public static LeilaoObjetoRegistrado from(LeilaoObjeto leilao) {
            return LeilaoObjetoRegistrado.builder()
                .id(leilao.getId())
                .objetos(leilao.getObjetos().stream()
                    .map(value -> new ObjetoRegistrado(value.getCategoria(), value.getDescricao()))
                    .collect(java.util.stream.Collectors.toSet()))
                .build();
        }
    }

    record ObjetoRegistrado(CategoriaId categoria, Descricao descricao) {

    }
}
