package br.com.offer.app.categoria.domain;

import static lombok.AccessLevel.PROTECTED;

import java.util.UUID;

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

@Table(name = "categoria")
@Entity
public class CategoriaQuery extends AbstractAggregateRoot<CategoriaQuery> {

    @Id
    private final UUID id;

    private final String descricao;

}
