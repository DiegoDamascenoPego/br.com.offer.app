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
@Schema(description = "País do endereço", example = "Brasil", type = "string", maxLength = 64)
public class Pais extends SimpleValueObject<String> {

    public static final String ATTR = "pais";

    @Size(max = 64, message = "{Pais.Size}")
    @Column(name = "pais")
    @JsonValue
    private final String value;

    private Pais(String pais) {
        this.value = pais;
    }

    public static Pais from(String pais) {
        return new Pais(pais);
    }


    public String asString() {
        return value;
    }
}
