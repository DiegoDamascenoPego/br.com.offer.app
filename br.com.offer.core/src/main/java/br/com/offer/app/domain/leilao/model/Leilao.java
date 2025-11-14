package br.com.offer.app.domain.leilao.model;

import static br.com.offer.app.domain.leilao.usecase.RegistrarLeilaoUseCase.LeilaoRegistrado;
import static java.util.Objects.requireNonNull;
import static lombok.AccessLevel.PROTECTED;

import java.math.BigDecimal;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.domain.AbstractAggregateRoot;

import lombok.Getter;
import lombok.NoArgsConstructor;

import br.com.offer.app.domain.sk.Descricao;
import br.com.offer.app.domain.sk.Observacao;

@Getter
@NoArgsConstructor(access = PROTECTED, force = true)

@DynamicUpdate
@Entity
@Table(name = "leilao")
public class Leilao extends AbstractAggregateRoot<Leilao> {

    @EmbeddedId
    @AttributeOverride(name = LeilaoId.ATTR, column = @Column(name = "id"))
    private LeilaoId id;

    private Descricao descricao;

    @Column(name = "localizacao", columnDefinition = "text")
    private String localizacao;

    private Observacao observacao;

    @Column(name = "lance_inicial", precision = 9, scale = 2)
    private BigDecimal lanceInicial;

    @Column(name = "lote", length = 64)
    private String lote;

    private Periodo periodo;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_lance", length = 64)
    private TipoLance tipoLance;

    public static LeilaoBuilder builder() {
        return new LeilaoBuilder();
    }

    public Leilao(LeilaoBuilder builder) {
        this.id = requireNonNull(builder.getId());
        this.descricao = requireNonNull(builder.getDescricao());
        this.localizacao = requireNonNull(builder.getLocalizacao());
        this.observacao = builder.getObservacao();
        this.lanceInicial = requireNonNull(builder.getLanceInicial());
        this.periodo = requireNonNull(builder.getPeriodo());
        this.tipoLance = requireNonNull(builder.getTipoLance());

        this.lote = builder.getLote();
        registerEvent(LeilaoRegistrado.from(this));
    }
}
