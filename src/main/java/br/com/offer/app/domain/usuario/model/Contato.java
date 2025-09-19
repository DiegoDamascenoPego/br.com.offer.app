package br.com.offer.app.domain.usuario.model;

import static java.util.Objects.requireNonNull;
import static lombok.AccessLevel.PROTECTED;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;

import br.com.offer.app.domain.sk.TipoContato;

@Getter
@NoArgsConstructor(access = PROTECTED, force = true)

@Entity
@Table(name = "pessoa_contato")
public class Contato {

    @EmbeddedId
    @AttributeOverride(name = ContatoId.ATTR, column = @Column(name = "id"))
    private final ContatoId id;

    private final TipoContato tipo;

    @NotBlank(message = "{ValorContato.NotBlank}")
    @Size(max = 256, message = "{ValorContato.Size}")
    @Column(name = "contato")
    private final String valor;

    public Contato(final ContatoId id, final TipoContato tipo, final String valor) {
        this.id = requireNonNull(id);
        this.tipo = requireNonNull(tipo);
        this.valor = requireNonNull(valor);
    }

    public static Contato of(final TipoContato tipo, final String valor) {
        return new Contato(ContatoId.generate(), tipo, valor);
    }
}
