package br.com.offer.app.domain.leilao.app;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import br.com.offer.app.domain.leilao.model.LeilaoObjeto;
import br.com.offer.app.domain.leilao.model.Objeto;
import br.com.offer.app.domain.leilao.repository.LeilaoObjetoRepository;
import br.com.offer.app.domain.leilao.usecase.RegistrarLeilaoObjetoUseCase;
import br.com.offer.infra.stream.Publisher;

@RequiredArgsConstructor

@Service
@Transactional
public class RegistrarLeilaoObjetoAppService implements RegistrarLeilaoObjetoUseCase {

    private final Publisher publisher;

    private final LeilaoObjetoRepository repository;

    @Override
    public void handle(final RegistrarLeilaoObjeto command) {

        final LeilaoObjeto leilaoObjeto = repository.getById(command.getId());

        command.getItems().stream().map(item -> Objeto.builder()
                .id(item.id())
                .categoria(item.categoria())
                .descricao(item.descricao())
                .build())
            .forEach(leilaoObjeto::addObjeto);

        repository.save(leilaoObjeto);
    }

    @Override
    @EventListener
    public void on(final LeilaoObjetoRegistrado event) {
            publisher.dispacth(event);
    }
}
