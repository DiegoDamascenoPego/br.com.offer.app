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
@Schema(description = "Estado do endereço", example = "SP", type = "string", maxLength = 2)
public class Estado extends SimpleValueObject<String> {

    public static final String ATTR = "estado";

    @Size(max = 2, message = "{Estado.Size}")
    @Column(name = "estado")
    @JsonValue
    private final String value;

    private Estado(String estado) {
        this.value = estado;
    }

    public static Estado from(String estado) {
        return new Estado(estado);
    }


    public String asString() {
        return value;
    }
}
