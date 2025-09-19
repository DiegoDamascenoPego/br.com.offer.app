package br.com.offer.app.domain.usuario.model;

import lombok.Getter;

import java.util.function.BiPredicate;
import java.util.function.Consumer;

import static java.util.Objects.requireNonNull;

import br.com.offer.app.domain.sk.Nome;
import br.com.offer.app.domain.sk.Documento;

@Getter
public class UsuarioBuilderUpdate {

    private final UsuarioId id;
    private final Consumer<UsuarioBuilderUpdate> action;

    protected Nome nome;
    protected Documento documento;
    protected TipoUsuario tipo;

    private BiPredicate<Nome, UsuarioId> nomeConstraint;
    private BiPredicate<Documento, UsuarioId> documentoConstraint;

    public UsuarioBuilderUpdate nome(Nome nome) {
        this.nome = nome;
        return this;
    }

    public UsuarioBuilderUpdate documento(Documento documento) {
        this.documento = documento;
        return this;
    }

    public UsuarioBuilderUpdate tipo(TipoUsuario tipo) {
        this.tipo = tipo;
        return this;
    }

    public UsuarioBuilderUpdate nomeDuplicatedConstraint(BiPredicate<Nome, UsuarioId> constraint) {
        this.nomeConstraint = constraint;
        return this;
    }

    public UsuarioBuilderUpdate documentoDuplicatedConstraint(BiPredicate<Documento, UsuarioId> constraint) {
        this.documentoConstraint = constraint;
        return this;
    }

    UsuarioBuilderUpdate(UsuarioId id, Consumer<UsuarioBuilderUpdate> action) {
        this.id = requireNonNull(id);
        this.action = requireNonNull(action);
    }

    public void apply() {
        // Como o Nome e Documento não têm método applyDuplicateConstraint, vamos implementar a validação aqui
        if (nomeConstraint != null && nomeConstraint.test(requireNonNull(nome), id)) {
            throw new IllegalArgumentException("Já existe um usuário com o nome: " + nome.getValue());
        }

        if (documentoConstraint != null && documentoConstraint.test(requireNonNull(documento), id)) {
            throw new IllegalArgumentException("Já existe um usuário com o documento: " + documento.getValue());
        }

        action.accept(this);
    }
}
