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
@Schema(description = "Complemento do endereço", example = "Apto 45", type = "string", maxLength = 128)
public class Complemento extends SimpleValueObject<String> {

    public static final String ATTR = "complemento";

    @Size(max = 128, message = "{Complemento.Size}")
    @Column(name = "complemento")
    @JsonValue
    private final String value;

    private Complemento(String complemento) {
        this.value = complemento;
    }

    public static Complemento from(String complemento) {
        return new Complemento(complemento);
    }


    public String asString() {
        return value;
    }
}
