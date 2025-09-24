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
@Schema(description = "Bairro do endereço", example = "Centro", type = "string", maxLength = 128)
public class Bairro extends SimpleValueObject<String> {

    public static final String ATTR = "bairro";

    @Size(max = 128, message = "{Bairro.Size}")
    @Column(name = "bairro")
    @JsonValue
    private final String value;

    private Bairro(String bairro) {
        this.value = bairro;
    }

    public static Bairro from(String bairro) {
        return new Bairro(bairro);
    }

    public String asString() {
        return value;
    }
}
