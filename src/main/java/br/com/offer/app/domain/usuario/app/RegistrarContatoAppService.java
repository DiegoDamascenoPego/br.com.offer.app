package br.com.offer.app.domain.usuario.app;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import br.com.offer.app.domain.usuario.model.ContatoId;
import br.com.offer.app.domain.usuario.model.Usuario;
import br.com.offer.app.domain.usuario.repository.UsuarioDomainRepository;
import br.com.offer.app.domain.usuario.usecase.RegistrarContatoUseCase;

@RequiredArgsConstructor

@Service
@Transactional
public class RegistrarContatoAppService implements RegistrarContatoUseCase {

    private final UsuarioDomainRepository repository;

    @Override
    public void handle(RegistrarContato command) {

        final Usuario usuario = repository.get(command.getUsuarioId());

        usuario.registrarContato(command.getTipo(), command.getContato());

        repository.save(usuario);
    }

    @Override
    @EventListener
    public void on(ContatoRegistrado event) {
        // Aqui poderia ser implementada a publicação de eventos
    }
}
