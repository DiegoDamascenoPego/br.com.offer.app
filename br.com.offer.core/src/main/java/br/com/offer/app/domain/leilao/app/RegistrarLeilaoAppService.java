package br.com.offer.app.domain.leilao.app;

import lombok.RequiredArgsConstructor;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.offer.app.domain.leilao.model.Leilao;
import br.com.offer.app.domain.leilao.model.LeilaoId;
import br.com.offer.app.domain.leilao.repository.LeilaoRepository;
import br.com.offer.app.domain.leilao.usecase.RegistrarLeilaoUseCase;
import br.com.offer.infra.stream.Publisher;

@RequiredArgsConstructor

@Service
@Transactional
public class RegistrarLeilaoAppService implements RegistrarLeilaoUseCase {

    private final Publisher publisher;

    private final LeilaoRepository leilaoRepository;

    @Override
    public LeilaoId handle(RegistrarLeilao command) {
        Leilao leilao = Leilao.builder()
            .descricao(command.getDescricao())
            .observacao(command.getObservacao())
            .localizacao(command.getLocalizacao())
            .lanceInicial(command.getLanceInicial())
            .lote(command.getLote())
            .periodo(command.getPeriodo())
            .tipoLance(command.getTipoLance())
            .build();

        leilaoRepository.save(leilao);

        return leilao.getId();
    }

    @Override
    @EventListener
    public void on(LeilaoRegistrado event) {
        publisher.dispacth(event);
    }
}
