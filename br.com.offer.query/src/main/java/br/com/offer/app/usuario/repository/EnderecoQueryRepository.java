package br.com.offer.app.usuario.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.repository.Repository;

import br.com.offer.app.usuario.domain.EnderecoQuery;

public interface EnderecoQueryRepository extends Repository<EnderecoQuery, UUID> {

    List<EnderecoQuery> findAllByUsuario(UUID usuario);
}
