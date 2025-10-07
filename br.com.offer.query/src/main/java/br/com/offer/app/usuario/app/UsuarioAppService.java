package br.com.offer.app.usuario.app;

import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import br.com.offer.app.usuario.projection.Usuario;
import br.com.offer.app.usuario.repository.UsuarioQueryRepository;

@RequiredArgsConstructor

@Service
@Transactional(readOnly = true)
public class UsuarioAppService {

    private final UsuarioQueryRepository usuarioQueryRepository;

    public Slice<Usuario> findAll(Pageable pageable) {
        return usuarioQueryRepository.findAllProjected(pageable);
    }

    public Usuario getByID(UUID id) {
        return usuarioQueryRepository.findByIdProjected(id)
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }
}
