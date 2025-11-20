package br.com.offer.app.domain.leilao.model;

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
@Schema(type = "string", format = "uuid", description = "Identificador único do objeto do leilão")
public class ObjetoId {

    public static final String ATTR = "id";

    @JsonValue
    UUID id;

    @JsonCreator
    private ObjetoId(final UUID id) {
        this.id = id;
    }

    public static ObjetoId from(final String id) {
        return new ObjetoId(UUID.fromString(id));
    }

    public static ObjetoId generate() {
        return new ObjetoId(UUID.randomUUID());
    }

    public String asString() {
        return  String.valueOf(id);
    }
}
