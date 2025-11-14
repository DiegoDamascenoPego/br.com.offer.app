package br.com.offer.app.domain.leilao.repository;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

import br.com.offer.app.domain.leilao.model.LeilaoObjeto;

@RequiredArgsConstructor

@Repository
public class LeilaoObjetoRepositoryImpl implements LeilaoObjetoRepository {

    private final JdbcClient jdbcClient;

    @Override
    public void save(final LeilaoObjeto leilaoObjeto) {
        leilaoObjeto.getObjetos().forEach(objeto -> {
            jdbcClient.sql(
                    "INSERT INTO objecto (objeto_id, leilao_id, categoria_id, descricao ) VALUES (:objetoId, :leilaoId, :categoriaId, :descricao)")
                .param("leilaoId", leilaoObjeto.getId())
                .param("objetoId", objeto.getId())
                .param("categoriaId", objeto.getCategoria())
                .param("descricao", objeto.getDescricao())
                .update();
        });
    }
}
