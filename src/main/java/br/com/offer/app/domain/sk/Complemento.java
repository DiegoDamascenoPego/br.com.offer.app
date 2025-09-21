package br.com.offer.app.domain.sk;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonValue;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@EqualsAndHashCode

@Embeddable
@Schema(description = "Complemento do endereço", example = "Apto 45", type = "string", maxLength = 128)
public class Complemento {

    public static final String ATTR = "complemento";

    @Size(max = 128, message = "{Complemento.Size}")
    @JsonValue
    private final String complemento;

    private Complemento(String complemento) {
        this.complemento = complemento != null ? complemento.trim() : null;
    }

    public static Complemento from(String complemento) {
        return new Complemento(complemento);
    }

    public String asString() {
        return complemento;
    }
}
