package br.com.offer.app.domain.usuario.model;

import static java.util.Objects.requireNonNull;
import static lombok.AccessLevel.PROTECTED;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import org.springframework.data.domain.AbstractAggregateRoot;

import lombok.Getter;
import lombok.NoArgsConstructor;

import br.com.offer.app.domain.sk.Bairro;
import br.com.offer.app.domain.sk.Cep;
import br.com.offer.app.domain.sk.Cidade;
import br.com.offer.app.domain.sk.Complemento;
import br.com.offer.app.domain.sk.Estado;
import br.com.offer.app.domain.sk.Logradouro;
import br.com.offer.app.domain.sk.Numero;
import br.com.offer.app.domain.sk.Pais;
import br.com.offer.app.domain.usuario.usecase.RegistrarEnderecoUseCase;

@Getter
@NoArgsConstructor(access = PROTECTED, force = true)

@Table(name = "usuario_endereco")
@Entity
public class Endereco extends AbstractAggregateRoot<Endereco> {

    @EmbeddedId
    @AttributeOverride(name = EnderecoId.ATTR, column = @Column(name = "id"))
    private final EnderecoId id;

    @AttributeOverride(name = UsuarioId.ATTR, column = @Column(name = "usuario_id", nullable = false))
    private final UsuarioId usuario;

    private final Logradouro logradouro;

    private final Numero numero;

    private final Complemento complemento;

    private final Bairro bairro;

    private final Cidade cidade;

    private final Estado estado;

    private final Cep cep;

    private final Pais pais;

    public static EnderecoBuilder builder() {
        return new EnderecoBuilder();
    }

    Endereco(EnderecoId id, UsuarioId usuario, Logradouro logradouro, Numero numero, Complemento complemento,
        Bairro bairro, Cidade cidade, Estado estado, Cep cep, Pais pais) {
        this.id = requireNonNull(id);
        this.usuario = requireNonNull(usuario);
        this.logradouro = logradouro;
        this.numero = numero;
        this.complemento = complemento;
        this.bairro = bairro;
        this.cidade = cidade;
        this.estado = estado;
        this.cep = cep;
        this.pais = pais;

        RegistrarEnderecoUseCase.EnderecoRegistrado.from(this);
    }

}
