package br.com.offer.app.categoria.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import br.com.offer.app.categoria.domain.CategoriaQuery;
import br.com.offer.app.categoria.projection.Categoria;

public interface CategoriaQueryRepository extends Repository<CategoriaQuery, UUID> {

    Slice<Categoria> findAllBy(Pageable pageable);

    Optional<Categoria> findProjectedById(@Param("id") UUID id);

}
