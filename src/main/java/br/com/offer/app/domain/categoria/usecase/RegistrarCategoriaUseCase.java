package br.com.offer.app.domain.categoria.usecase;

import static lombok.AccessLevel.PRIVATE;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import lombok.Builder;
import lombok.Value;

import br.com.offer.app.domain.categoria.model.CategoriaId;
import br.com.offer.app.domain.sk.Descricao;

public interface RegistrarCategoriaUseCase {

    CategoriaId handle(RegistrarCategoria command);

    void on(CategoriaRegistrada event);

    @Value
    @Builder
    class RegistrarCategoria {

        @Valid
        @NotNull(message = "{Categoria.descricao.NotNull}")
        Descricao descricao;
    }

    @Value
    @Builder(access = PRIVATE)
    class CategoriaRegistrada {

        CategoriaId id;
        Descricao descricao;

        public static CategoriaRegistrada from(br.com.offer.app.domain.categoria.model.Categoria categoria) {
            return CategoriaRegistrada.builder()
                .id(categoria.getId())
                .descricao(categoria.getDescricao())
                .build();
        }
    }
}
