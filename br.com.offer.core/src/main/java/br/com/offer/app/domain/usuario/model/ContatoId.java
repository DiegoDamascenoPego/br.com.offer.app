package br.com.offer.app.domain.usuario.model;

import java.util.UUID;

import jakarta.persistence.Embeddable;

import com.fasterxml.jackson.annotation.JsonValue;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Value;

@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@Value

@Embeddable
@Schema(type = "string", format = "uuid", description = "Identificador único do contato")
public class ContatoId {

    public static final String ATTR = "id";

    @JsonValue
    UUID id;

    private ContatoId(final UUID id) {
        this.id = id;
    }

    public static ContatoId generate() {
        return new ContatoId(UUID.randomUUID());
    }

    public static ContatoId from(String value) {
        return new ContatoId(UUID.fromString(value));
    }

    public String asString() {
        return id.toString();
    }

    public RuntimeException notFoundException() {
        return new RuntimeException("Contato not found: " + id);
    }
}
