package br.com.offer.app.usuario.domain;

import static lombok.AccessLevel.PROTECTED;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import org.springframework.data.annotation.Immutable;
import org.springframework.data.domain.AbstractAggregateRoot;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = PROTECTED, force = true)

@Immutable

@Table(name = "usuario_endereco")
@Entity
public class EnderecoQuery extends AbstractAggregateRoot<EnderecoQuery> {

    @Id
    private final UUID id;

    @Column(name = "usuario_id")
    private final UUID usuario;

    private final String logradouro;

    private final String numero;

    private final String complemento;

    private final String bairro;

    private final String cidade;

    private final String estado;

    private final String cep;

    private final String pais;

}
