package br.com.offer.app.domain.usuario.app;

import lombok.RequiredArgsConstructor;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.offer.app.domain.usuario.model.Usuario;
import br.com.offer.app.domain.usuario.model.UsuarioId;
import br.com.offer.app.domain.usuario.repository.UsuarioDomainRepository;
import br.com.offer.app.domain.usuario.usecase.RegistrarUsuarioUseCase;

@RequiredArgsConstructor

@Service
@Transactional
public class RegistrarUsuarioAppService implements RegistrarUsuarioUseCase {

    private final UsuarioDomainRepository usuarioDomainRepository;

    @Override
    public UsuarioId handle(RegistrarUsuario command) {
        Usuario usuario = Usuario.builder()
            .nome(command.getNome())
            .documento(command.getDocumento())
            .tipo(command.getTipo())
            .documentoExistsConstraint(usuarioDomainRepository::existsByDocumentoAndIdNot)
            .build();

        usuarioDomainRepository.save(usuario);

        return usuario.getId();
    }

    @Override
    @EventListener
    public void on(UsuarioRegistrado event) {
        // Aqui você pode adicionar lógica adicional quando um usuário for registrado
        // Por exemplo, enviar email de boas-vindas, criar log de auditoria, etc.
    }
}
