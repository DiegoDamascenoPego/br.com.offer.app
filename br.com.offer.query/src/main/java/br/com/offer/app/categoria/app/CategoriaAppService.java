package br.com.offer.app.categoria.app;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import br.com.offer.app.categoria.projection.Categoria;
import br.com.offer.app.categoria.repository.CategoriaQueryRepository;

@RequiredArgsConstructor

@Service
@Transactional(readOnly = true)
public class CategoriaAppService  {

    private final CategoriaQueryRepository repository;


    public Categoria getByID(UUID id) {
        return repository.findProjectedById(id)
            .orElseThrow(() -> new IllegalArgumentException("Categoria não encontrada"));
    }


    public Slice<Categoria> findAll(Pageable pageable) {
        return repository.findAllBy(pageable);
    }
}
