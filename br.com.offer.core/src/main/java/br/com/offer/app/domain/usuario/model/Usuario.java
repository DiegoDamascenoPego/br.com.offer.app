package br.com.offer.app.domain.usuario.model;

import static br.com.offer.app.domain.usuario.usecase.RegistrarUsuarioUseCase.UsuarioRegistrado;
import static java.util.Objects.requireNonNull;
import static lombok.AccessLevel.PROTECTED;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.domain.AbstractAggregateRoot;

import lombok.Getter;
import lombok.NoArgsConstructor;

import br.com.offer.app.domain.sk.Documento;
import br.com.offer.app.domain.sk.Nome;

@Getter
@NoArgsConstructor(access = PROTECTED, force = true)

@DynamicUpdate
@Entity
@Table(name = "usuario")
public class Usuario extends AbstractAggregateRoot<Usuario> {

    @EmbeddedId
    @AttributeOverride(name = UsuarioId.ATTR, column = @Column(name = "id"))
    private UsuarioId id;

    private Nome nome;

    private Documento documento;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_usuario")
    private TipoUsuario tipo;

    @Column(name = "deleted")
    private boolean deleted;

    public static UsuarioBuilder builder() {
        return new UsuarioBuilder();
    }

    public Usuario(UsuarioBuilder builder) {
        this.id = requireNonNull(builder.id);
        this.nome = requireNonNull(builder.nome);
        this.documento = requireNonNull(builder.documento);
        this.tipo = requireNonNull(builder.tipo);

        registerEvent(UsuarioRegistrado.from(this));
    }
}
