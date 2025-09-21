package br.com.offer.app.domain.usuario.repository;

import java.util.List;

import org.springframework.data.repository.Repository;

import br.com.offer.app.domain.usuario.model.Endereco;
import br.com.offer.app.domain.usuario.model.EnderecoId;
import br.com.offer.app.domain.usuario.model.UsuarioId;

public interface EnderecoRepository extends Repository<Endereco, EnderecoId> {

    void save(Endereco endereco);

    List<Endereco> findAllByUsuarioId(UsuarioId usuarioId);
}
