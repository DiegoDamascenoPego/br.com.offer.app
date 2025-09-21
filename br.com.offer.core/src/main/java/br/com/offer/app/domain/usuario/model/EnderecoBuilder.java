package br.com.offer.app.domain.usuario.model;

import static java.util.Objects.requireNonNull;

import java.util.function.Predicate;

import br.com.offer.app.domain.sk.Bairro;
import br.com.offer.app.domain.sk.Cep;
import br.com.offer.app.domain.sk.Cidade;
import br.com.offer.app.domain.sk.Complemento;
import br.com.offer.app.domain.sk.Estado;
import br.com.offer.app.domain.sk.Logradouro;
import br.com.offer.app.domain.sk.Numero;
import br.com.offer.app.domain.sk.Pais;

public class EnderecoBuilder {

    private EnderecoId id;
    private UsuarioId usuarioId;
    private Logradouro logradouro;
    private Numero numero;
    private Complemento complemento;
    private Bairro bairro;
    private Cidade cidade;
    private Estado estado;
    private Cep cep;
    private Pais pais;
    private Predicate<UsuarioId> usuarioExistsConstraints;

    public EnderecoBuilder usuarioId(UsuarioId usuarioId) {
        this.usuarioId = usuarioId;
        return this;
    }

    public EnderecoBuilder logradouro(Logradouro logradouro) {
        this.logradouro = logradouro;
        return this;
    }

    public EnderecoBuilder numero(Numero numero) {
        this.numero = numero;
        return this;
    }

    public EnderecoBuilder complemento(Complemento complemento) {
        this.complemento = complemento;
        return this;
    }

    public EnderecoBuilder bairro(Bairro bairro) {
        this.bairro = bairro;
        return this;
    }

    public EnderecoBuilder cidade(Cidade cidade) {
        this.cidade = cidade;
        return this;
    }

    public EnderecoBuilder estado(Estado estado) {
        this.estado = estado;
        return this;
    }

    public EnderecoBuilder cep(Cep cep) {
        this.cep = cep;
        return this;
    }

    public EnderecoBuilder pais(Pais pais) {
        this.pais = pais;
        return this;
    }

    public EnderecoBuilder usuarioExistsConstraints(final Predicate<UsuarioId> usuarioExistsConstraints) {

        this.usuarioExistsConstraints = usuarioExistsConstraints;
        return this;
    }

    public Endereco build() {
        this.id = EnderecoId.generate();

        if (!usuarioExistsConstraints.test(usuarioId)) {
            throw usuarioId.notFoundException();
        }

        return new Endereco(id, requireNonNull(usuarioId), logradouro, numero, complemento,
            bairro, cidade, estado, cep, pais);
    }

}
