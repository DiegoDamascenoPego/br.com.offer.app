package br.com.offer.infra.openapi;

import org.springframework.lang.Nullable;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;

@Getter
public class SwaggerPageable {

    @Parameter(
        description = "Página que você deseja recuperar (0..N)",
        example = "0",
        schema = @Schema(type = "integer", defaultValue = "0")
    )
    @Nullable
    private Integer page;

    @Parameter(
        description = "Número de registros por página",
        example = "20",
        schema = @Schema(type = "integer", defaultValue = "20")
    )
    @Nullable
    private Integer size;

    @Parameter(
        description = "Critério de ordenação no formato: propriedade(,asc|desc). A ordem padrão é crescente. Múltiplos critérios de ordenação são suportados.",
        example = "descricao,asc",
        schema = @Schema(type = "string")
    )
    @Nullable
    private String sort;

}
