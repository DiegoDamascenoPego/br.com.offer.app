package br.com.offer.app.domain.leilao.repository;

import java.util.Set;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

import br.com.offer.app.domain.categoria.model.CategoriaId;
import br.com.offer.app.domain.leilao.model.LeilaoId;
import br.com.offer.app.domain.leilao.model.LeilaoObjeto;
import br.com.offer.app.domain.leilao.model.Objeto;
import br.com.offer.app.domain.leilao.model.ObjetoId;
import br.com.offer.app.domain.sk.Descricao;
import br.com.offer.infra.stream.PublisherDomainEvents;

@RequiredArgsConstructor

@Repository
public class LeilaoObjetoRepositoryImpl implements LeilaoObjetoRepository {

    private final JdbcClient jdbcClient;
    private final PublisherDomainEvents publisherDomainEvents;

    @Override
    public LeilaoObjeto getById(final LeilaoId id) {
        Set<Objeto> objetos = jdbcClient.sql("SELECT * FROM objeto WHERE leilao_id = :leilaoId")
            .param("leilaoId", id.getId())
            .query((rs, row) -> Objeto.builder()
                .id(ObjetoId.from((rs.getString("id"))))
                .categoria(CategoriaId.from(rs.getString("categoria_id")))
                .descricao(Descricao.from(rs.getString("descricao")))
                .build())
            .set();

        return new LeilaoObjeto(id, objetos);
    }

    @Override
    public void save(final LeilaoObjeto leilaoObjeto) {
        leilaoObjeto.getObjetos().forEach(objeto -> {
            jdbcClient.sql(
                    "INSERT INTO objeto (id, leilao_id, categoria_id, descricao) " +
                        "VALUES (:objetoId, :leilaoId, :categoriaId, :descricao) " +
                        "ON CONFLICT (id) DO UPDATE SET " +
                        "categoria_id = EXCLUDED.categoria_id, " +
                        "descricao = EXCLUDED.descricao")
                .param("leilaoId", leilaoObjeto.getId().getId())
                .param("objetoId", objeto.getId().getId())
                .param("categoriaId", objeto.getCategoria().getId())
                .param("descricao", objeto.getDescricao().asString())
                .update();
        });

        publisherDomainEvents.dispacth(leilaoObjeto);
    }
}
