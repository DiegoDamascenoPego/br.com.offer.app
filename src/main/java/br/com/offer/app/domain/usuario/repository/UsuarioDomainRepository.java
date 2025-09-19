package br.com.offer.app.domain.usuario.repository;

import java.util.Optional;

import org.springframework.data.repository.Repository;

import br.com.offer.app.domain.sk.Documento;
import br.com.offer.app.domain.usuario.model.Usuario;
import br.com.offer.app.domain.usuario.model.UsuarioId;

public interface UsuarioDomainRepository extends Repository<Usuario, UsuarioId> {

    void save(Usuario usuario);

    Optional<Usuario> findById(UsuarioId id);

    default Usuario get(UsuarioId id) {
        return findById(id).orElseThrow(id::notFoundException);
    }

    boolean existsByDocumentoAndIdNot(Documento documento, UsuarioId id);
}
