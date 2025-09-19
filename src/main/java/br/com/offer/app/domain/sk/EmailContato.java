package br.com.offer.app.domain.sk;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
@Embeddable
public class EmailContato {

    public static final String ATTR = "value";

    @NotBlank(message = "{Email.NotBlank}")
    @Email(message = "{Email.Invalid}")
    @Size(max = 255, message = "{Email.Size}")
    @Column(name = "email")
    String value;

    public static EmailContato from(String value) {
        return new EmailContato(value);
    }
}
