package br.com.offer.app.domain.leilao.usecase;

import static lombok.AccessLevel.PRIVATE;

import java.util.Set;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import lombok.Builder;
import lombok.Value;

import br.com.offer.app.domain.categoria.model.CategoriaId;
import br.com.offer.app.domain.leilao.model.LeilaoId;
import br.com.offer.app.domain.leilao.model.LeilaoObjeto;
import br.com.offer.app.domain.sk.Descricao;

public interface RegistrarLeilaoObjetoUseCase {

    LeilaoId handle(LeilaoId leilao, Set<RegistrarLeilaoObjeto> objetos);

    void on(LeilaoObjetoRegistrado event);

    @Value
    @Builder
    class RegistrarLeilaoObjeto {

        CategoriaId categoria;

        @Valid
        @NotNull(message = "{Leilao.descricao.NotNull}")
        Descricao descricao;

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
