package br.com.offer.app.domain.leilao.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

import jakarta.persistence.Embeddable;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(force = true, access = lombok.AccessLevel.PRIVATE)

@Embeddable
@Schema(description = "Período do leilão")
public class Periodo implements Serializable {

    @Schema(description = "Data e hora de início do leilão", example = "2024-01-15T10:00:00", type = "string", format = "date-time")
    private final LocalDateTime inicio;

    @Schema(description = "Data e hora de término do leilão", example = "2024-01-15T18:00:00", type = "string", format = "date-time")
    private final LocalDateTime fim;

    private Periodo(final LocalDateTime inicio, final LocalDateTime fim) {
        this.inicio = Objects.requireNonNull(inicio);
        this.fim = Objects.requireNonNull(fim);

    }

    public static Periodo of(final LocalDateTime inicio, final LocalDateTime fim) {
        return new Periodo(inicio, fim);
    }
}
