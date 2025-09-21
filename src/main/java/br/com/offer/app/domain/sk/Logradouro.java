package br.com.offer.app.domain.sk;

import static java.util.Objects.requireNonNull;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
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
@Schema(description = "Logradouro do endereço", example = "Rua das Flores", type = "string", maxLength = 256)
public class Logradouro {

    public static final String ATTR = "logradouro";

    @NotBlank(message = "{Logradouro.NotBlank}")
    @Size(max = 256, message = "{Logradouro.Size}")
    @JsonValue
    private final String logradouro;

    private Logradouro(String logradouro) {
        this.logradouro = requireNonNull(logradouro).trim();
    }

    public static Logradouro from(String logradouro) {
        return new Logradouro(logradouro);
    }

    public String asString() {
        return logradouro;
    }
}
