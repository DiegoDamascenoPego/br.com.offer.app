package br.com.offer.app.domain.usuario.model;

import static java.util.Objects.requireNonNull;

import java.util.function.BiPredicate;

import br.com.offer.app.domain.sk.Documento;
import br.com.offer.app.domain.sk.Nome;

public class UsuarioBuilder {

    protected UsuarioId id;
    protected Nome nome;
    protected Documento documento;
    protected TipoUsuario tipo;

    private BiPredicate<Documento, UsuarioId> documentoExistsConstraint;

    public UsuarioBuilder nome(Nome nome) {
        this.nome = nome;
        return this;
    }

    public UsuarioBuilder documento(Documento documento) {
        this.documento = documento;
        return this;
    }

    public UsuarioBuilder tipo(TipoUsuario tipo) {
        this.tipo = tipo;
        return this;
    }

    public UsuarioBuilder documentoExistsConstraint(BiPredicate<Documento, UsuarioId> constraint) {
        this.documentoExistsConstraint = constraint;
        return this;
    }

    public Usuario build() {
        id = UsuarioId.generate();

        if (documentoExistsConstraint.test(requireNonNull(documento), id)) {
            throw new IllegalArgumentException("Já existe um usuário com o documento: " + documento.getValue());
        }

        return new Usuario(this);
    }
}
