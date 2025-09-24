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
@Schema(description = "Cidade do endereço", example = "São Paulo", type = "string", maxLength = 128)
public class Cidade extends SimpleValueObject<String> {

    public static final String ATTR = "cidade";

    @Size(max = 128, message = "{Cidade.Size}")
    @Column(name = "cidade")
    @JsonValue
    private final String value;

    private Cidade(String cidade) {
        this.value = cidade;
    }

    public static Cidade from(String cidade) {
        return new Cidade(cidade);
    }

    public String asString() {
        return value;
    }
}
