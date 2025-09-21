package br.com.offer.app.domain.usuario.repository;

import org.springframework.data.repository.Repository;

import br.com.offer.app.domain.usuario.model.Contato;
import br.com.offer.app.domain.usuario.model.ContatoId;

public interface ContatoRepository extends Repository<Contato, ContatoId> {

    void save(Contato contato);
}
