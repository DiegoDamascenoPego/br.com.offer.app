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
@Schema(description = "Bairro do endereço", example = "Centro", type = "string", maxLength = 128)
public class Bairro {

    public static final String ATTR = "bairro";

    @Size(max = 128, message = "{Bairro.Size}")
    @JsonValue
    private final String bairro;

    private Bairro(String bairro) {
        this.bairro = bairro != null ? bairro.trim() : null;
    }

    public static Bairro from(String bairro) {
        return new Bairro(bairro);
    }

    public String asString() {
        return bairro;
    }
}
