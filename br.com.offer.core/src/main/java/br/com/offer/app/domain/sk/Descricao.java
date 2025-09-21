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
public class Descricao {

    public static final String ATTR = "value";

    @NotBlank(message = "{Descricao.NotBlank}")
    @Size(max = 128, message = "{Descricao.Size}")
    @Column(name = "descricao")
    String value;

    public static Descricao from(String value) {
        return new Descricao(value);
    }
}
