package br.com.offer.app.domain.usuario.app;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import br.com.offer.app.domain.usuario.model.Contato;
import br.com.offer.app.domain.usuario.model.ContatoId;
import br.com.offer.app.domain.usuario.repository.ContatoRepository;
import br.com.offer.app.domain.usuario.repository.UsuarioDomainRepository;
import br.com.offer.app.domain.usuario.usecase.RegistrarContatoUseCase;

@RequiredArgsConstructor

@Service
@Transactional
public class RegistrarContatoAppService implements RegistrarContatoUseCase {

    private final ContatoRepository repository;
    private final UsuarioDomainRepository usuarioDomainRepository;

    @Override
    public ContatoId handle(RegistrarContato command) {

        final Contato contato = Contato.builder()
            .tipo(command.getTipo())
            .valor(command.getContato())
            .usuario(command.getUsuario())
            .usuarioExistscontraint(usuarioDomainRepository::existsByIdAndDeletedFalse)
            .build();

        repository.save(contato);

        return contato.getId();
    }

    @Override
    @EventListener
    public void on(ContatoRegistrado event) {
        // Aqui poderia ser implementada a publicação de eventos
    }
}
