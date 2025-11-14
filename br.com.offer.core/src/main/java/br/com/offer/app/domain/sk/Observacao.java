package br.com.offer.app.domain.sk;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonValue;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@Embeddable

@Schema(description = "Observações do leilão", example = "Leilão com visitação prévia obrigatória", type = "string", maxLength = 128)
public class Observacao extends SimpleValueObject<String> {

    public static final String ATTR = "value";

    @NotBlank(message = "{Observacao.NotBlank}")
    @Size(max = 128, message = "{Observacao.Size}")
    @Column(name = "observacao")
    @JsonValue
    final String value;

    private Observacao(final String value) {
        this.value = value;
    }

    public static Observacao from(String value) {
        return new Observacao(value);
    }
}
