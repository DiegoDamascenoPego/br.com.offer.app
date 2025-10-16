package br.com.offer.app.domain.leilao.repository;

import java.util.Optional;

import org.springframework.data.repository.Repository;

import br.com.offer.app.domain.leilao.model.Leilao;
import br.com.offer.app.domain.leilao.model.LeilaoId;

public interface LeilaoRepository extends Repository<Leilao, LeilaoId> {

    void save(Leilao leilao);

    Optional<Leilao> findById(LeilaoId id);

    default Leilao get(LeilaoId id) {
        return findById(id).orElseThrow(() -> new RuntimeException("Leilão não encontrado"));
    }

    boolean existsById(LeilaoId id);
}
