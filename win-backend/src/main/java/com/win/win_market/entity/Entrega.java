package com.win.win_market.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "entregas")
public class Entrega {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    @JoinColumn(name = "pedido_id")
    private long pedidoId;
    private String status;
    @Column(name = "data_retirada")
    private String dataRetirada;
    @Column(name = "data_entrega")
    private String dataEntrega;
    @Column(name = "codigo_confirmacao_retirada")
    private String codigoConfirmacaoRetirada;
    @Column(name = "codigo_confirmacao_entrega")
    private String codigoConfirmacaoEntrega;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getPedidoId() {
        return pedidoId;
    }

    public void setPedidoId(long pedidoId) {
        this.pedidoId = pedidoId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDataRetirada() {
        return dataRetirada;
    }

    public void setDataRetirada(String dataRetirada) {
        this.dataRetirada = dataRetirada;
    }

    public String getDataEntrega() {
        return dataEntrega;
    }

    public void setDataEntrega(String dataEntrega) {
        this.dataEntrega = dataEntrega;
    }

    public String getCodigoConfirmacaoRetirada() {
        return codigoConfirmacaoRetirada;
    }

    public void setCodigoConfirmacaoRetirada(String codigoConfirmacaoRetirada) {
        this.codigoConfirmacaoRetirada = codigoConfirmacaoRetirada;
    }

    public String getCodigoConfirmacaoEntrega() {
        return codigoConfirmacaoEntrega;
    }

    public void setCodigoConfirmacaoEntrega(String codigoConfirmacaoEntrega) {
        this.codigoConfirmacaoEntrega = codigoConfirmacaoEntrega;
    }
}


