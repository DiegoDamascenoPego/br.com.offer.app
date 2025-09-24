package br.com.offer.app.domain.sk;

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
@Schema(description = "Nome", type = "string", example = "João da Silva", maxLength = 256)
public class Nome extends SimpleValueObject<String> {

    public static final String ATTR = "value";

    @NotBlank(message = "{Nome.NotBlank}")
    @Size(max = 256, message = "{Nome.Size}")
    @Column(name = "nome")
    @JsonValue
    final String value;

    private Nome(final String value) {
        this.value = value;
    }

    public static Nome from(String value) {
        return new Nome(value);
    }
}
