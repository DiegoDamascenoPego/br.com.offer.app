package br.com.offer.app.domain.leilao.model;

import static java.util.Objects.requireNonNull;
import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;

import java.util.Objects;

import lombok.Getter;
import lombok.NoArgsConstructor;

import br.com.offer.app.domain.categoria.model.CategoriaId;
import br.com.offer.app.domain.sk.Descricao;

@Getter
@NoArgsConstructor(access = PRIVATE, force = true)

public class Objeto {

    private ObjetoId id;

    private Descricao descricao;

    private CategoriaId categoria;

    public Objeto(final ObjetoBuilder builder) {
        this.id = Objects.requireNonNullElse(builder.getId(), ObjetoId.generate());
        this.descricao = requireNonNull(builder.getDescricao());
        this.categoria = requireNonNull(builder.getCategoria());
    }

    public static ObjetoBuilder builder() {
        return new ObjetoBuilder();
    }

    @Override
    public boolean equals(final Object o) {
        if (o == null || getClass() != o.getClass())
            return false;
        final Objeto objeto = (Objeto) o;
        return Objects.equals(id, objeto.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
