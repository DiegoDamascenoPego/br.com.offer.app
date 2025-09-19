package br.com.offer.app.domain.usuario.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Value;

import java.util.UUID;

@Value
@AllArgsConstructor
@Embeddable
@Schema(type = "string", format = "uuid", description = "Identificador único do contato")
public class ContatoId {

    public static final String ATTR = "id";

    UUID id;

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
