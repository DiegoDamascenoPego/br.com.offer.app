package br.com.offer.app.domain.leilao.model;

import static lombok.AccessLevel.PROTECTED;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

import lombok.Getter;
import lombok.NoArgsConstructor;

import br.com.offer.app.domain.categoria.model.CategoriaId;
import br.com.offer.app.domain.sk.Descricao;

@Getter
@NoArgsConstructor(access = PROTECTED, force = true)

@DynamicUpdate
@Entity
@Table(name = "leilao")
public class Objeto {

    @EmbeddedId
    @AttributeOverride(name = LeilaoId.ATTR, column = @Column(name = "id"))
    private LeilaoId id;

    private Descricao descricao;

    @AttributeOverride(name = CategoriaId.ATTR, column = @Column(name = "categoria_id"))
    private CategoriaId categoria;
}
