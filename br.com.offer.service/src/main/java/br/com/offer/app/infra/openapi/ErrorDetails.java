package br.com.offer.app.infra.openapi;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;

@Getter

@Schema(description = "Detalhes sobre um erro ocorrido na API")
public class ErrorDetails {

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    String code;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    String message;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    String detailedMessage;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    String type;

    public ErrorDetails() {
    }

    public ErrorDetails(String code, String message, String detailedMessage, String type) {
        this.code = code;
        this.message = message;
        this.detailedMessage = detailedMessage;
        this.type = type;
    }
}