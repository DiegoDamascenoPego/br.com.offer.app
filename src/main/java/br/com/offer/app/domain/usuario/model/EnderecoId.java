package br.com.offer.app.domain.usuario.model;

import java.util.UUID;

import jakarta.persistence.Embeddable;

import com.fasterxml.jackson.annotation.JsonValue;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Value;

@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@Value

@Embeddable
@Schema(type = "string", format = "uuid", description = "Identificador único do endereço")
public class EnderecoId {

    public static final String ATTR = "id";

    @JsonValue
    UUID id;

    public EnderecoId(final UUID id) {
        this.id = id;
    }

    public static EnderecoId from(String id) {
        return new EnderecoId(UUID.fromString(id));
    }

    public static EnderecoId from(UUID id) {
        return new EnderecoId(id);
    }

    public static EnderecoId generate() {
        return new EnderecoId(UUID.randomUUID());
    }

    public String asString() {
        return id.toString();
    }

    public RuntimeException notFoundException() {
        return new RuntimeException("Endereço não encontrado: " + id);
    }
}
