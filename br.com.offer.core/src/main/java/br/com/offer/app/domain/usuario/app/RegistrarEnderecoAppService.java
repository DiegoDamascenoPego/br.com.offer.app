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

@RequiredArgsConstructor

@Service
@Transactional
public class RegistrarEnderecoAppService implements RegistrarEnderecoUseCase {
    private final UsuarioDomainRepository usuarioDomainRepository;
    private final EnderecoRepository enderecoRepository;

    @Override
    public EnderecoId handle(RegistrarEndereco command) {

        Endereco endereco = Endereco.builder()
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
        // Aqui você pode implementar lógica adicional quando um endereço é registrado
        // Por exemplo, publicar eventos para outros bounded contexts
    }

}
