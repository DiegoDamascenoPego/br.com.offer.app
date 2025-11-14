package br.com.offer.app.domain.leilao.model;

import java.util.Set;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import lombok.Getter;

@Getter

@Entity
@Table(name = "leilao")
public class LeilaoObjeto {

    @EmbeddedId
    @AttributeOverride(name = LeilaoId.ATTR, column = @Column(name = "id"))
    private LeilaoId id;

    @OneToMany
    Set<Objeto> objetos;

    public LeilaoObjeto(final Set<Objeto> objetos) {
        this.objetos = objetos;
    }
}
