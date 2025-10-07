package br.com.offer.app.usuario.domain;

import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import org.springframework.data.annotation.Immutable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = PRIVATE, force = true)

@Immutable

@Table(name = "usuario_contato")
@Entity
public class ContatoQuery {

    @Id
    private final UUID id;

    private final String contato;

    private final String tipo;

    @Column(name = "usuario_id")
    private final UUID usuario;
}
