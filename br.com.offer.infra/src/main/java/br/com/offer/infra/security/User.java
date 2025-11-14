package br.com.offer.infra.security;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder

@Entity
@Table(name = "usuario")
public class User {

    @Id
    private UUID id;

    private String nome;

    private String documento;

    private String tipoUsuario;

}
