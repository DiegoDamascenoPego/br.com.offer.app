package br.com.offer.app.domain.leilao.model;

import java.math.BigDecimal;

import lombok.Getter;

import br.com.offer.app.domain.sk.Descricao;
import br.com.offer.app.domain.sk.Observacao;

@Getter
public class LeilaoBuilder {

    private LeilaoId id;
    private Descricao descricao;
    private String localizacao;
    private Observacao observacao;
    private BigDecimal lanceInicial;
    private String lote;
    private TipoLance tipoLance;
    private Periodo periodo;

    public LeilaoBuilder descricao(Descricao descricao) {
        this.descricao = descricao;
        return this;
    }

    public LeilaoBuilder localizacao(String localizacao) {
        this.localizacao = localizacao;
        return this;
    }

    public LeilaoBuilder observacao(Observacao observacao) {
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

    public LeilaoBuilder periodo(Periodo periodo) {
        this.periodo = periodo;
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
