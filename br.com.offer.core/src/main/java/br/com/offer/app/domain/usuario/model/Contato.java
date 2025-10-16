package br.com.offer.app.domain.usuario.model;

import static br.com.offer.app.domain.usuario.usecase.RegistrarContatoUseCase.*;
import static java.util.Objects.requireNonNull;
import static lombok.AccessLevel.PROTECTED;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import org.springframework.data.domain.AbstractAggregateRoot;

import lombok.Getter;
import lombok.NoArgsConstructor;

import br.com.offer.app.domain.sk.TipoContato;
import br.com.offer.app.domain.usuario.usecase.RegistrarContatoUseCase;

@Getter
@NoArgsConstructor(access = PROTECTED, force = true)

@Entity
@Table(name = "usuario_contato")
public class Contato extends AbstractAggregateRoot<Contato> {

    @EmbeddedId
    @AttributeOverride(name = ContatoId.ATTR, column = @Column(name = "id"))
    private final ContatoId id;

    @AttributeOverride(name = UsuarioId.ATTR, column = @Column(name = "usuario_id", nullable = false))
    private final UsuarioId usuario;

    @Enumerated(EnumType.STRING)
    private final TipoContato tipo;

    @NotBlank(message = "{ValorContato.NotBlank}")
    @Size(max = 256, message = "{ValorContato.Size}")
    @Column(name = "contato")
    private final String valor;

    public Contato(final ContatoId id, final UsuarioId usuario, final TipoContato tipo, final String valor) {
        this.id = requireNonNull(id);
        this.usuario = requireNonNull(usuario);
        this.tipo = requireNonNull(tipo);
        this.valor = requireNonNull(valor);

        registerEvent(ContatoRegistrado.from(this));
    }

    public static ContatoBuilder builder() {
        return new ContatoBuilder();
    }

}
