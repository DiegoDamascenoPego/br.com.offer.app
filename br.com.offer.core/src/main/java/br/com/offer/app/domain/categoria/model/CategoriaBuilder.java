package br.com.offer.app.domain.categoria.model;

import static java.util.Objects.requireNonNull;

import java.util.function.BiPredicate;

import lombok.Getter;

import br.com.offer.app.domain.sk.Descricao;

@Getter
public class CategoriaBuilder {

    private CategoriaId id;
    private Descricao descricao;

    private BiPredicate<Descricao, CategoriaId> descricaoConstraint;

    public CategoriaBuilder descricao(Descricao descricao) {
        this.descricao = descricao;
        return this;
    }

    public CategoriaBuilder descricaoDuplicatedConstraint(BiPredicate<Descricao, CategoriaId> constraint) {
        this.descricaoConstraint = constraint;
        return this;
    }

    public Categoria build() {
        id = CategoriaId.generate();

        // Como o Descricao não tem método applyDuplicateConstraint, vamos implementar a validação aqui
        if (descricaoConstraint != null && descricaoConstraint.test(requireNonNull(descricao), id)) {
            throw new IllegalArgumentException("Já existe uma categoria com a descrição: " + descricao.getValue());
        }

        return new Categoria(this);
    }
}
