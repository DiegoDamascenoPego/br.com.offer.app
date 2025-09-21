package br.com.offer.app.domain.sk;

import jakarta.persistence.Embeddable;
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
@Schema(description = "Cidade do endereço", example = "São Paulo", type = "string", maxLength = 128)
public class Cidade {

    public static final String ATTR = "cidade";

    @Size(max = 128, message = "{Cidade.Size}")
    @JsonValue
    private final String cidade;

    private Cidade(String cidade) {
        this.cidade = cidade != null ? cidade.trim() : null;
    }

    public static Cidade from(String cidade) {
        return new Cidade(cidade);
    }

    public String asString() {
        return cidade;
    }
}
