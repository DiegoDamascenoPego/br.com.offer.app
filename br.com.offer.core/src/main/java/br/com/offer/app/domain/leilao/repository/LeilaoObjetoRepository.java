package br.com.offer.app.domain.leilao.repository;

import br.com.offer.app.domain.leilao.model.LeilaoId;
import br.com.offer.app.domain.leilao.model.LeilaoObjeto;

public interface LeilaoObjetoRepository {

    LeilaoObjeto getById(LeilaoId id);

    void save(LeilaoObjeto leilaoObjeto);
}
