package br.com.offer.app.domain.categoria.model;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

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
@Schema(type = "string", format = "uuid", description = "Identificador único da categoria")
public class CategoriaId implements Serializable {

    public static final String ATTR = "id";

    @Serial private static final long serialVersionUID = 4003447708600454200L;

    @JsonValue
    UUID id;



    @JsonCreator
    public CategoriaId(UUID value) {
        this.id = value;
    }

    public static CategoriaId generate() {
        return new CategoriaId(UUID.randomUUID());
    }

    public String asString() {
        return id.toString();
    }

    public RuntimeException notFoundException() {
        return new RuntimeException("Categoria not found: " + id);
    }
}
