package br.com.offer.app.domain.sk;

import jakarta.persistence.Embeddable;
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
@Schema(description = "Estado do endereço", example = "SP", type = "string", maxLength = 2)
public class Estado {

    public static final String ATTR = "estado";

    @Size(max = 2, message = "{Estado.Size}")
    @JsonValue
    private final String estado;

    private Estado(String estado) {
        this.estado = estado != null ? estado.trim().toUpperCase() : null;
    }

    public static Estado from(String estado) {
        return new Estado(estado);
    }

    public String asString() {
        return estado;
    }
}
