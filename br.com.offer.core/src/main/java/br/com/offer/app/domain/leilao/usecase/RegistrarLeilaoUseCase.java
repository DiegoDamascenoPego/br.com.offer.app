package br.com.offer.app.domain.leilao.usecase;

import static lombok.AccessLevel.PRIVATE;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Builder;
import lombok.Value;

import br.com.offer.app.domain.leilao.model.Leilao;
import br.com.offer.app.domain.leilao.model.LeilaoId;
import br.com.offer.app.domain.leilao.model.TipoLance;

public interface RegistrarLeilaoUseCase {

    LeilaoId handle(RegistrarLeilao command);

    void on(LeilaoRegistrado event);

    @Value
    @Builder
    class RegistrarLeilao {

        @NotNull(message = "{Leilao.descricao.NotNull}")
        @Size(max = 128, message = "{Leilao.descricao.Size}")
        @Schema(description = "Descrição do leilão", example = "Leilão de imóveis urbanos", type = "string", maxLength = 128)
        String descricao;

        @Size(max = 128, message = "{Leilao.observacao.Size}")
        @Schema(description = "Observações do leilão", example = "Leilão com visitação prévia obrigatória", type = "string", maxLength = 128)
        String observacao;

        @Schema(description = "Localização do leilão", example = "Rua das Flores, 123 - São Paulo/SP", type = "string")
        String localizacao;

        @PositiveOrZero(message = "{Leilao.lanceInicial.PositiveOrZero}")
        @Schema(description = "Lance inicial do leilão", example = "100000.50", type = "number", format = "decimal")
        BigDecimal lanceInicial;

        @Size(max = 64, message = "{Leilao.lote.Size}")
        @Schema(description = "Identificação do lote", example = "LOTE-001", type = "string", maxLength = 64)
        String lote;

        @NotNull(message = "{Leilao.inicio.NotNull}")
        @Schema(description = "Data e hora de início do leilão", example = "2024-01-15T10:00:00", type = "string", format = "date-time")
        LocalDateTime inicio;

        @NotNull(message = "{Leilao.termino.NotNull}")
        @Schema(description = "Data e hora de término do leilão", example = "2024-01-15T18:00:00", type = "string", format = "date-time")
        LocalDateTime termino;

        @Valid
        @NotNull(message = "{Leilao.tipoLance.NotNull}")
        @Schema(description = "Tipo de lance do leilão", example = "ABERTO")
        TipoLance tipoLance;
    }

    @Value
    @Builder(access = PRIVATE)
    class LeilaoRegistrado {

        LeilaoId id;
        String descricao;
        String observacao;
        String localizacao;
        BigDecimal lanceInicial;
        String lote;
        LocalDateTime inicio;
        LocalDateTime termino;
        TipoLance tipoLance;

        public static LeilaoRegistrado from(Leilao leilao) {
            return LeilaoRegistrado.builder()
                .id(leilao.getId())
                .descricao(leilao.getDescricao())
                .observacao(leilao.getObservacao())
                .localizacao(leilao.getLocalizacao())
                .lanceInicial(leilao.getLanceInicial())
                .lote(leilao.getLote())
                .inicio(leilao.getInicio())
                .termino(leilao.getTermino())
                .tipoLance(leilao.getTipoLance())
                .build();
        }
    }
}
