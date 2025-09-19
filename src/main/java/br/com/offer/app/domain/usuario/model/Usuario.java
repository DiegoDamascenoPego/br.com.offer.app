package br.com.offer.app.domain.usuario.model;

import static br.com.offer.app.domain.usuario.usecase.RegistrarUsuarioUseCase.UsuarioRegistrado;
import static java.util.Objects.requireNonNull;
import static lombok.AccessLevel.PROTECTED;

import java.util.Set;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.domain.AbstractAggregateRoot;

import lombok.Getter;
import lombok.NoArgsConstructor;

import br.com.offer.app.domain.sk.Documento;
import br.com.offer.app.domain.sk.Nome;
import br.com.offer.app.domain.sk.TipoContato;
import br.com.offer.app.domain.usuario.usecase.RegistrarContatoUseCase;

@Getter
@NoArgsConstructor(access = PROTECTED, force = true)

@DynamicUpdate
@Entity
@Table(name = "pessoa")
public class Usuario extends AbstractAggregateRoot<Usuario> {

    @EmbeddedId
    @AttributeOverride(name = UsuarioId.ATTR, column = @Column(name = "id"))
    private UsuarioId id;

    private Nome nome;

    private Documento documento;

    @Enumerated
    private TipoUsuario tipo;

    @ElementCollection
    @CollectionTable(name = "contrato_analise_qualidade", joinColumns = @JoinColumn(name = "pessoa_id"))
    Set<Contato> contatos;

    @Column(name = "deleted")
    private boolean deleted = false;

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

    public UsuarioBuilderUpdate alterar() {
        return new UsuarioBuilderUpdate(id, form -> {
            nome = requireNonNull(form.getNome());
            documento = requireNonNull(form.getDocumento());
            tipo = requireNonNull(form.getTipo());
        });
    }

    public void remover() {
        this.deleted = true;
    }

    public void registrarContato(TipoContato tipo, String contato) {
        contatos.add(Contato.of(tipo, contato));
        registerEvent(RegistrarContatoUseCase.ContatoRegistrado.from(this));
    }
}
