package br.com.offer.app.domain.sk;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonValue;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Value;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@Embeddable

@Schema(description = "Descrição", example = "Descrição do item", type = "string", maxLength = 128)
public class Descricao extends SimpleValueObject<String> {

    public static final String ATTR = "value";

    @NotBlank(message = "{Descricao.NotBlank}")
    @Size(max = 128, message = "{Descricao.Size}")
    @Column(name = "descricao")
    @JsonValue
    final String value;

    private Descricao(final String value) {
        this.value = value;
    }

    public static Descricao from(String value) {
        return new Descricao(value);
    }
}
