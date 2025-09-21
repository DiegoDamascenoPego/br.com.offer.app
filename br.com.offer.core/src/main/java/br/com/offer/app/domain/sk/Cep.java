package br.com.offer.app.domain.sk;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonValue;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@EqualsAndHashCode
@ToString

@Embeddable
@Schema(description = "CEP do endereço", example = "01234-567", type = "string", maxLength = 10)
public class Cep {

    public static final String ATTR = "cep";

    @Size(max = 10, message = "{Cep.Size}")
    @Pattern(regexp = "\\d{5}-?\\d{3}", message = "{Cep.Pattern}")
    @JsonValue
    private final String cep;

    private Cep(String cep) {
        this.cep = cep != null ? cep.trim().replaceAll("[^\\d]", "") : null;
    }

    public static Cep from(String cep) {
        return new Cep(cep);
    }

    public String asString() {
        return cep;
    }

    public String asFormattedString() {
        if (cep != null && cep.length() == 8) {
            return cep.substring(0, 5) + "-" + cep.substring(5);
        }
        return cep;
    }
}
