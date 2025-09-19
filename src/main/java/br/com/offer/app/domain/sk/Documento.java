package br.com.offer.app.domain.sk;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
@Embeddable
public class Documento {

    public static final String ATTR = "value";

    @NotBlank(message = "{Documento.NotBlank}")
    @Size(max = 128, message = "{Documento.Size}")
    @Column(name = "documento")
    String value;

    public static Documento from(String value) {
        return new Documento(value);
    }
}
