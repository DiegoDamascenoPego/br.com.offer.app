package br.com.offer.app.domain.categoria.model;

import static java.util.Objects.requireNonNull;
import static lombok.AccessLevel.PRIVATE;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import br.com.offer.app.domain.sk.Descricao;

@Getter
@NoArgsConstructor(access = PRIVATE, force = true)

@Entity
public class Categoria {

    @EmbeddedId
    @AttributeOverride(name = "id", column = @Column(name = "id"))
    private final CategoriaId id;

    private final Descricao descricao;

    private boolean deleted;

    public static CategoriaBuilder builder() {
        return new CategoriaBuilder();
    }

    public Categoria(CategoriaBuilder builder) {
        this.id = requireNonNull(builder.getId());
        this.descricao = requireNonNull(builder.getDescricao());
    }

    public void remover() {
        this.deleted = true;
    }
}
