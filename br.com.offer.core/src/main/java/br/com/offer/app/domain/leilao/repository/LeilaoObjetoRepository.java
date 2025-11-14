package br.com.offer.app.domain.leilao.repository;

import java.util.UUID;

import org.springframework.data.repository.Repository;

import br.com.offer.app.domain.leilao.model.Leilao;
import br.com.offer.app.domain.leilao.model.LeilaoObjeto;

public interface LeilaoObjetoRepository extends Repository<LeilaoObjeto, UUID> {


    void save(LeilaoObjeto leilaoObjeto);
}
