package br.com.offer.app.domain.sk;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonValue;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@EqualsAndHashCode
@ToString

@Embeddable
@Schema(description = "Número do endereço", example = "123", type = "string", maxLength = 32)
public class Numero {

    public static final String ATTR = "numero";

    @Size(max = 32, message = "{Numero.Size}")
    @JsonValue
    private final String numero;

    private Numero(String numero) {
        this.numero = numero != null ? numero.trim() : null;
    }

    public static Numero from(String numero) {
        return new Numero(numero);
    }

    public String asString() {
        return numero;
    }
}
