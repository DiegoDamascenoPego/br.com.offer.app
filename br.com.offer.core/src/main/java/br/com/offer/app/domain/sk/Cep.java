package br.com.offer.app.domain.sk;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonValue;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)

@Embeddable
@Schema(description = "CEP do endereço", example = "01234-567", type = "string", maxLength = 10)
public class Cep extends SimpleValueObject<String> {

    public static final String ATTR = "cep";

    @Size(max = 10, message = "{Cep.Size}")
    @Pattern(regexp = "\\d{5}-?\\d{3}", message = "{Cep.Pattern}")
    @Column(name = "cep")
    @JsonValue
    private final String value;

    private Cep(String cep) {
        this.value = cep;
    }

    public static Cep from(String cep) {
        return new Cep(cep);
    }

    public String asString() {
        return value;
    }
}
