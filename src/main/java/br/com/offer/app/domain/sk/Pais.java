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
@Schema(description = "País do endereço", example = "Brasil", type = "string", maxLength = 64)
public class Pais {

    public static final String ATTR = "pais";

    @Size(max = 64, message = "{Pais.Size}")
    @JsonValue
    private final String pais;

    private Pais(String pais) {
        this.pais = pais != null ? pais.trim() : null;
    }

    public static Pais from(String pais) {
        return new Pais(pais);
    }

    public String asString() {
        return pais;
    }
}
