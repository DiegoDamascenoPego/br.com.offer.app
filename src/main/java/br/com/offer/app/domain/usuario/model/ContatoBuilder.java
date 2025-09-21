package br.com.offer.app.domain.usuario.model;

import java.util.function.Predicate;

import br.com.offer.app.domain.sk.TipoContato;

public class ContatoBuilder {
    private ContatoId id;
    private UsuarioId usuario;
    private TipoContato tipo;
    private String valor;
    private Predicate<UsuarioId> usuarioExistscontraint;

    ContatoBuilder() {
    }

    public ContatoBuilder id(final ContatoId id) {
        this.id = id;
        return this;
    }

    public ContatoBuilder usuario(final UsuarioId usuarioId) {
        this.usuario = usuarioId;
        return this;
    }

    public ContatoBuilder tipo(final TipoContato tipo) {
        this.tipo = tipo;
        return this;
    }

    public ContatoBuilder valor(final String valor) {
        this.valor = valor;
        return this;
    }

    public ContatoBuilder usuarioExistscontraint(
        final Predicate<UsuarioId> usuarioExistscontraint) {
        this.usuarioExistscontraint = usuarioExistscontraint;
        return this;
    }

    public Contato build() {

        usuario.applyExistsConstraint(usuarioExistscontraint);

        return new Contato(id, usuario, tipo, valor);
    }
}
