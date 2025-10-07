package br.com.offer.app.usuario.domain;

import static lombok.AccessLevel.PROTECTED;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import org.springframework.data.annotation.Immutable;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = PROTECTED, force = true)

@Immutable

@Table(name = "usuario")
@Entity
public class UsuarioQuery {

    @Id
    private final UUID id;

    private final String nome;
    private final String documento;

    @Column(name = "tipo_usuario")
    private final String tipo;
}
