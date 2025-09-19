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
public class Nome {

    public static final String ATTR = "value";

    @NotBlank(message = "{Nome.NotBlank}")
    @Size(max = 256, message = "{Nome.Size}")
    @Column(name = "nome")
    String value;

    public static Nome from(String value) {
        return new Nome(value);
    }
}
