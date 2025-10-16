package br.com.offer.app.domain.usuario.app;// Criar novo endereço

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import br.com.offer.app.domain.usuario.model.Endereco;
import br.com.offer.app.domain.usuario.model.EnderecoId;
import br.com.offer.app.domain.usuario.repository.EnderecoRepository;
import br.com.offer.app.domain.usuario.repository.UsuarioDomainRepository;
import br.com.offer.app.domain.usuario.usecase.RegistrarEnderecoUseCase;
import br.com.offer.infra.stream.Publisher;

@RequiredArgsConstructor

@Service
@Transactional
public class RegistrarEnderecoAppService implements RegistrarEnderecoUseCase {

    private final Publisher publisher;

    private final UsuarioDomainRepository usuarioDomainRepository;
    private final EnderecoRepository enderecoRepository;

    @Override
    public EnderecoId handle(RegistrarEndereco command) {

        Endereco endereco = Endereco.builder()
            .usuarioId(command.getUsuario())
            .cep(command.getCep())
            .pais(command.getPais())
            .estado(command.getEstado())
            .cidade(command.getCidade())
            .bairro(command.getBairro())
            .logradouro(command.getLogradouro())
            .numero(command.getNumero())
            .complemento(command.getComplemento())
            .usuarioExistsConstraints(usuarioDomainRepository::existsByIdAndDeletedFalse)
            .build();

        enderecoRepository.save(endereco);

        return endereco.getId();

    }

    @Override
    @EventListener
    public void on(EnderecoRegistrado event) {
        publisher.dispacth(event);
    }

}
