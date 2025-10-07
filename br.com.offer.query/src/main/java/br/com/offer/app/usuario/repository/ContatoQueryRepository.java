package br.com.offer.app.usuario.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.offer.app.usuario.domain.ContatoQuery;
import br.com.offer.app.usuario.projection.Contato;

@Repository
public interface ContatoQueryRepository extends JpaRepository<ContatoQuery, UUID> {

    @Query("""
        SELECT new br.com.offer.app.usuario.projection.Contato(
            c.id,
            c.contato,
            c.tipo
        )
        FROM ContatoQuery c
        WHERE c.usuario = :usuarioId
        ORDER BY c.contato
        """)
    Slice<Contato> findByUsuarioProjected(@Param("usuarioId") UUID usuarioId, Pageable pageable);

    @Query("""
        SELECT new br.com.offer.app.usuario.projection.Contato(
            c.id,
            c.contato,
            c.tipo
        )
        FROM ContatoQuery c
        WHERE c.id = :id
        """)
    Optional<Contato> findByIdProjected(@Param("id") UUID id);
}
