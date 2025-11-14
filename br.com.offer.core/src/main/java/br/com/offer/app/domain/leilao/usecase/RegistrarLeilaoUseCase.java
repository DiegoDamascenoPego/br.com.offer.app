package br.com.offer.app.domain.leilao.usecase;

import static lombok.AccessLevel.PRIVATE;

import java.math.BigDecimal;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Builder;
import lombok.Value;

import br.com.offer.app.domain.leilao.model.Leilao;
import br.com.offer.app.domain.leilao.model.LeilaoId;
import br.com.offer.app.domain.leilao.model.Periodo;
import br.com.offer.app.domain.leilao.model.TipoLance;
import br.com.offer.app.domain.sk.Descricao;
import br.com.offer.app.domain.sk.Observacao;

public interface RegistrarLeilaoUseCase {

    LeilaoId handle(RegistrarLeilao command);

    void on(LeilaoRegistrado event);

    @Value
    @Builder
    class RegistrarLeilao {

        @Valid
        @NotNull(message = "{Leilao.descricao.NotNull}")
        Descricao descricao;

        Observacao observacao;

        @Schema(description = "Localização do leilão", example = "Rua das Flores, 123 - São Paulo/SP", type = "string")
        String localizacao;

        @PositiveOrZero(message = "{Leilao.lanceInicial.PositiveOrZero}")
        @Schema(description = "Lance inicial do leilão", example = "100000.50", type = "number", format = "decimal")
        BigDecimal lanceInicial;

        @Size(max = 64, message = "{Leilao.lote.Size}")
        @Schema(description = "Identificação do lote", example = "LOTE-001", type = "string", maxLength = 64)
        String lote;

        @Valid
        @NotNull(message = "{Leilao.periodo.NotNull}")
        Periodo periodo;

        @Valid
        @NotNull(message = "{Leilao.tipoLance.NotNull}")
        @Schema(description = "Tipo de lance do leilão", example = "ABERTO")
        TipoLance tipoLance;
    }

    @Value
    @Builder(access = PRIVATE)
    class LeilaoRegistrado {

        LeilaoId id;
        Descricao descricao;
        Observacao observacao;
        String localizacao;
        BigDecimal lanceInicial;
        String lote;
        Periodo periodo;
        TipoLance tipoLance;

        public static LeilaoRegistrado from(Leilao leilao) {
            return LeilaoRegistrado.builder()
                .id(leilao.getId())
                .descricao(leilao.getDescricao())
                .observacao(leilao.getObservacao())
                .localizacao(leilao.getLocalizacao())
                .lanceInicial(leilao.getLanceInicial())
                .lote(leilao.getLote())
                .periodo(leilao.getPeriodo())
                .tipoLance(leilao.getTipoLance())
                .build();
        }
    }
}
