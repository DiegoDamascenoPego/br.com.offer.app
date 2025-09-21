package br.com.offer.app.usuario.app;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import br.com.offer.app.usuario.repository.EnderecoQueryRepository;
import br.com.offer.app.usuario.usecase.ListarEnderecosUseCase;

@RequiredArgsConstructor

@Service
@Transactional(readOnly = true)
public class ListarEnderecosAppService implements ListarEnderecosUseCase {

    private final EnderecoQueryRepository repository;

    @Override
    public List<ListagemEndereco> handle(final UUID usuario) {
        var enderecos = repository.findAllByUsuario(usuario);

        return enderecos
            .stream()
            .map(ListagemEndereco::from)
            .collect(Collectors.toList());
    }
}
