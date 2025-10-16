package br.com.offer.app.domain.leilao.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Getter;

@Getter
public class LeilaoBuilder {

    private LeilaoId id;
    private String descricao;
    private String localizacao;
    private String observacao;
    private BigDecimal lanceInicial;
    private String lote;
    private LocalDateTime inicio;
    private LocalDateTime termino;
    private TipoLance tipoLance;

    public LeilaoBuilder descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public LeilaoBuilder localizacao(String localizacao) {
        this.localizacao = localizacao;
        return this;
    }

    public LeilaoBuilder observacao(String observacao) {
        this.observacao = observacao;
        return this;
    }

    public LeilaoBuilder lanceInicial(BigDecimal lanceInicial) {
        this.lanceInicial = lanceInicial;
        return this;
    }

    public LeilaoBuilder lote(String lote) {
        this.lote = lote;
        return this;
    }

    public LeilaoBuilder inicio(LocalDateTime inicio) {
        this.inicio = inicio;
        return this;
    }

    public LeilaoBuilder termino(LocalDateTime termino) {
        this.termino = termino;
        return this;
    }

    public LeilaoBuilder tipoLance(TipoLance tipoLance) {
        this.tipoLance = tipoLance;
        return this;
    }

    public Leilao build() {

        this.id = LeilaoId.generate();

        return new Leilao(this);
    }
}
