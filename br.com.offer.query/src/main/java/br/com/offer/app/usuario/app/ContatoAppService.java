package br.com.offer.app.usuario.app;

import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import br.com.offer.app.usuario.projection.Contato;
import br.com.offer.app.usuario.repository.ContatoQueryRepository;

@RequiredArgsConstructor

@Service
@Transactional(readOnly = true)
public class ContatoAppService {

    private final ContatoQueryRepository contatoQueryRepository;

    public Slice<Contato> findByUsuario(UUID usuarioId, Pageable pageable) {
        return contatoQueryRepository.findByUsuarioProjected(usuarioId, pageable);
    }

    public Contato getByID(UUID id) {
        return contatoQueryRepository.findByIdProjected(id)
            .orElseThrow(() -> new RuntimeException("Contato não encontrado"));
    }
}
