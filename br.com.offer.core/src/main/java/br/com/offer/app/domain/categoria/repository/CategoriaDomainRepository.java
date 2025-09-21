package br.com.offer.app.domain.categoria.repository;

import br.com.offer.app.domain.categoria.model.Categoria;
import br.com.offer.app.domain.categoria.model.CategoriaId;
import br.com.offer.app.domain.sk.Descricao;

import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface CategoriaDomainRepository extends Repository<Categoria, CategoriaId> {

    void save(Categoria categoria);

    boolean existsByDescricaoAndIdNot(Descricao descricao, CategoriaId id);

    Optional<Categoria> findById(CategoriaId id);

    default Categoria get(CategoriaId id) {
        return findById(id).orElseThrow(id::notFoundException);
    }
}
