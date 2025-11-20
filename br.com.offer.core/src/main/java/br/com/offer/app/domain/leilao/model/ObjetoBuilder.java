package br.com.offer.app.domain.leilao.model;

import lombok.Getter;

import br.com.offer.app.domain.categoria.model.CategoriaId;
import br.com.offer.app.domain.sk.Descricao;

@Getter
public class ObjetoBuilder {

    private ObjetoId id;
    private Descricao descricao;
    private CategoriaId categoria;

    public ObjetoBuilder id(final ObjetoId id) {
        this.id = id;
        return this;
    }

    public ObjetoBuilder descricao(final Descricao descricao) {
        this.descricao = descricao;
        return this;
    }

    public ObjetoBuilder categoria(final CategoriaId categoria) {
        this.categoria = categoria;
        return this;
    }

    public Objeto build() {
        return new Objeto(this);
    }
}