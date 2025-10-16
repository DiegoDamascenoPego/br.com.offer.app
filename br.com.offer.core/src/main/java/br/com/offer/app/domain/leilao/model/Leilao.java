package br.com.offer.app.domain.leilao.model;

import static br.com.offer.app.domain.leilao.usecase.RegistrarLeilaoUseCase.LeilaoRegistrado;
import static java.util.Objects.requireNonNull;
import static lombok.AccessLevel.PROTECTED;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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

@Getter
@NoArgsConstructor(access = PROTECTED, force = true)

@DynamicUpdate
@Entity
@Table(name = "leilao")
public class Leilao extends AbstractAggregateRoot<Leilao> {

    @EmbeddedId
    @AttributeOverride(name = LeilaoId.ATTR, column = @Column(name = "id"))
    private LeilaoId id;

    @Column(name = "descricao", length = 128)
    private String descricao;

    @Column(name = "localizacao", columnDefinition = "text")
    private String localizacao;

    @Column(name = "observacao", length = 128)
    private String observacao;

    @Column(name = "lance_inicial", precision = 9, scale = 2)
    private BigDecimal lanceInicial;

    @Column(name = "lote", length = 64)
    private String lote;

    @Column(name = "inicio")
    private LocalDateTime inicio;

    @Column(name = "termino")
    private LocalDateTime termino;

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
        this.inicio = requireNonNull(builder.getInicio());
        this.termino = requireNonNull(builder.getTermino());
        this.tipoLance = requireNonNull(builder.getTipoLance());

        this.lote = builder.getLote();
        registerEvent(LeilaoRegistrado.from(this));
    }
}
