package br.com.offer.app.domain.categoria.app;

import br.com.offer.app.domain.categoria.model.Categoria;
import br.com.offer.app.domain.categoria.model.CategoriaId;
import br.com.offer.app.domain.categoria.repository.CategoriaDomainRepository;
import br.com.offer.app.domain.categoria.usecase.RegistrarCategoriaUseCase;
import br.com.offer.infra.stream.Publisher;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class RegistrarCategoriaAppService implements RegistrarCategoriaUseCase {

    private final Publisher publisher;

    private final CategoriaDomainRepository categoriaDomainRepository;

    @Override
    public CategoriaId handle(RegistrarCategoria command) {
        Categoria categoria = Categoria.builder()
            .descricao(command.getDescricao())
            .descricaoDuplicatedConstraint(categoriaDomainRepository::existsByDescricaoAndIdNot)
            .build();

        categoriaDomainRepository.save(categoria);

        return categoria.getId();
    }

    @Override
    @EventListener
    public void on(CategoriaRegistrada event) {
        publisher.dispacth(event);
    }
}
