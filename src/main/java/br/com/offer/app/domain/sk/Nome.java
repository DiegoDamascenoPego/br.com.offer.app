package br.com.offer.app.domain.sk;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonValue;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Value;

@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@Value
@Embeddable
public class Nome {

    public static final String ATTR = "value";

    @NotBlank(message = "{Nome.NotBlank}")
    @Size(max = 256, message = "{Nome.Size}")
    @Column(name = "nome")
    @JsonValue
    String value;

    public Nome(final String value) {
        this.value = value;
    }

    public static Nome from(String value) {
        return new Nome(value);
    }
}
