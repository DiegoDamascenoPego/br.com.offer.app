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
import lombok.Value;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)


@Embeddable
@Schema(name = "Documento", description = "Documento de identificação, CPF ou CNPJ", type = "string", example = "CPF:13831100004", maxLength = 128)
public class Documento extends SimpleValueObject<String> {

    public static final String ATTR = "value";

    @NotBlank(message = "{Documento.NotBlank}")
    @Size(max = 128, message = "{Documento.Size}")
    @Column(name = "documento")
    @JsonValue
    final String value;

    private Documento(final String value) {
        this.value = value;
    }

    public static Documento from(String value) {
        return new Documento(value);
    }
}
