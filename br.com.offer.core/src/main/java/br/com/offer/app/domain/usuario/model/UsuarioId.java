package br.com.offer.app.domain.usuario.model;

import java.util.UUID;
import java.util.function.Predicate;

import jakarta.persistence.Embeddable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Value;

@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@Value

@Embeddable
@Schema(type = "string", format = "uuid", description = "Identificador único do usuário")
public class UsuarioId {

    public static final String ATTR = "id";

    @JsonValue
    UUID id;

    @JsonCreator
    private UsuarioId(final UUID id) {
        this.id = id;
    }

    public static UsuarioId generate() {
        return new UsuarioId(UUID.randomUUID());
    }

    public static UsuarioId from(String value) {
        return new UsuarioId(UUID.fromString(value));
    }

    public String asString() {
        return id.toString();
    }

    public void applyExistsConstraint(Predicate<UsuarioId> constraint) {
        if (!constraint.test(this))
            throw notFoundException();
    }

    public RuntimeException notFoundException() {
        return new RuntimeException("Usuário not found: " + id);
    }
}
