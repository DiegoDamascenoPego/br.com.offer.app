package br.com.offer.app.domain.leilao.model;

import static br.com.offer.app.domain.leilao.usecase.RegistrarLeilaoObjetoUseCase.LeilaoObjetoRegistrado;

import java.util.Set;

import org.springframework.data.domain.AbstractAggregateRoot;

import lombok.Getter;

@Getter

public class LeilaoObjeto extends AbstractAggregateRoot<Leilao> {

    private final LeilaoId id;

    private final Set<Objeto> objetos;

    public LeilaoObjeto(final LeilaoId id, final Set<Objeto> objetos) {
        this.id = id;
        this.objetos = objetos;

        registerEvent(LeilaoObjetoRegistrado.from(this));
    }

    public void addObjeto(Objeto objeto) {
        objetos.add(objeto);
    }
}
