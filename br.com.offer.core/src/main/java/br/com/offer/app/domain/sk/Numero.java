package br.com.offer.app.domain.sk;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonValue;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)

@Embeddable
@Schema(description = "Número do endereço", example = "123", type = "string", maxLength = 32)
public class Numero extends SimpleValueObject<String> {

    public static final String ATTR = "numero";

    @Size(max = 32, message = "{Numero.Size}")
    @Column(name = "numero")
    @JsonValue
    private final String value;

    private Numero(String numero) {
        this.value = numero;
    }

    public static Numero from(String numero) {
        return new Numero(numero);
    }

    public String asString() {
        return value;
    }
}
