package br.com.offer.app.domain.usuario.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Value;

import java.util.UUID;

@Value
@AllArgsConstructor
@Embeddable
@Schema(type = "string", format = "uuid", description = "Identificador único do usuário")
public class UsuarioId {

    public static final String ATTR = "id";

    UUID id;

    public static UsuarioId generate() {
        return new UsuarioId(UUID.randomUUID());
    }

    public static UsuarioId from(String value) {
        return new UsuarioId(UUID.fromString(value));
    }

    public String asString() {
        return id.toString();
    }

    public RuntimeException notFoundException() {
        return new RuntimeException("Usuário not found: " + id);
    }
}
