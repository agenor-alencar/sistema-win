package com.win.win_market.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "entregas")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Entrega {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pedido_id", nullable = false, unique = true)
    private Pedido pedido;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "motorista_id")
    private Motorista motorista;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "endereco_entrega_id")
    private Endereco enderecoEntrega;

    @Column(length = 50)
    private String status;

    @Column(name = "previsao_entrega", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private OffsetDateTime previsaoEntrega;

    @Column(name = "data_entrega", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private OffsetDateTime dataEntrega;

    @Column(name = "comprovante_url", columnDefinition = "TEXT")
    private String comprovanteUrl;

    @Column(columnDefinition = "TEXT")
    private String observacoes;

    @Column(name = "data_atualizacao", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    @UpdateTimestamp
    private OffsetDateTime dataAtualizacao;
}
