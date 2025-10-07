package br.com.offer.app.usuario.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.offer.app.usuario.domain.UsuarioQuery;
import br.com.offer.app.usuario.projection.Usuario;

@Repository
public interface UsuarioQueryRepository extends JpaRepository<UsuarioQuery, UUID> {

    @Query("""
        SELECT new br.com.offer.app.usuario.projection.Usuario(
            u.id,
            u.nome,
            u.documento,
            u.tipo
        )
        FROM UsuarioQuery u
        """)
    Slice<Usuario> findAllProjected(Pageable pageable);

    @Query("""
        SELECT new br.com.offer.app.usuario.projection.Usuario(
            u.id,
            u.nome,
            u.documento,
            u.tipo
        )
        FROM UsuarioQuery u
        WHERE u.id = :id
        """)
    Optional<Usuario> findByIdProjected(@Param("id") UUID id);
}
