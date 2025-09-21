package br.com.offer.app.domain.sk;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Value;
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@Value

@Embeddable
public class Documento {

    public static final String ATTR = "value";

    @NotBlank(message = "{Documento.NotBlank}")
    @Size(max = 128, message = "{Documento.Size}")
    @Column(name = "documento")
    String value;

    public Documento(final String value) {
        this.value = value;
    }

    public static Documento from(String value) {
        return new Documento(value);
    }
}
