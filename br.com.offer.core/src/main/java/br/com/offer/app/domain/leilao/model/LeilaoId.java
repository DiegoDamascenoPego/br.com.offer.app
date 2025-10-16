package br.com.offer.app.domain.leilao.model;

import java.util.UUID;
import java.util.function.Predicate;

import jakarta.persistence.Embeddable;

import com.fasterxml.jackson.annotation.JsonValue;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Value;

@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@Value

@Embeddable
@Schema(type = "string", format = "uuid", description = "Identificador único do leilão")
public class LeilaoId {

    public static final String ATTR = "id";

    @JsonValue
    UUID id;

    private LeilaoId(final UUID id) {
        this.id = id;
    }

    public static LeilaoId of(final UUID id) {
        return new LeilaoId(id);
    }

    public static LeilaoId generate() {
        return new LeilaoId(UUID.randomUUID());
    }

    public static Predicate<LeilaoId> equalTo(final LeilaoId id) {
        return candidate -> candidate.equals(id);
    }
}
