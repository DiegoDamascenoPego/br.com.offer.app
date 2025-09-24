package br.com.offer.app.domain.sk;

import static java.util.Objects.requireNonNull;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonValue;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)

@Embeddable
@Schema(description = "Logradouro do endereço", example = "Rua das Flores", type = "string", maxLength = 256)
public class Logradouro extends SimpleValueObject<String> {

    public static final String ATTR = "logradouro";

    @NotBlank(message = "{Logradouro.NotBlank}")
    @Size(max = 256, message = "{Logradouro.Size}")
    @Column(name = "logradouro")
    @JsonValue
    private final String value;

    private Logradouro(String logradouro) {
        this.value = requireNonNull(logradouro).trim();
    }

    public static Logradouro from(String logradouro) {
        return new Logradouro(logradouro);
    }


    public String asString() {
        return value;
    }
}
