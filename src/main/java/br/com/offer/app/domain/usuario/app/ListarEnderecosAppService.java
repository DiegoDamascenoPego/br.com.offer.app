package br.com.offer.app.domain.usuario.app;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import br.com.offer.app.domain.usuario.repository.EnderecoRepository;
import br.com.offer.app.domain.usuario.usecase.ListarEnderecosUseCase;

@RequiredArgsConstructor

@Service
@Transactional(readOnly = true)
public class ListarEnderecosAppService implements ListarEnderecosUseCase {

    private final EnderecoRepository repository;

    @Override
    public List<EnderecoResponse> handle(ListarEnderecos query) {
        var enderecos = repository.findAllByUsuarioId(query.getUsuarioId());

        return enderecos
            .stream()
            .map(EnderecoResponse::from)
            .collect(Collectors.toList());
    }
}
