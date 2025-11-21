package com.rachapp.app.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "pagamentos")
public class Pagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pagamento")
    private Long idPagamento;

    // Relationship: Payment belongs to a Racha
    @ManyToOne
    @JoinColumn(name = "id_racha", nullable = false)
    private Racha racha;

    // Relationship: Who paid? (The Debtor paying off debt)
    @ManyToOne
    @JoinColumn(name = "id_devedor", nullable = false)
    private Usuario devedor;

    // Relationship: Who received? (The Creditor)
    @ManyToOne
    @JoinColumn(name = "id_credor", nullable = false)
    private Usuario credor;

    @Column(nullable = false)
    private BigDecimal valor;

    // Automatically sets the time when saved
    @CreationTimestamp
    @Column(name = "data", updatable = false)
    private LocalDateTime data;

    // --- Constructors ---
    public Pagamento() {}

    public Pagamento(Racha racha, Usuario devedor, Usuario credor, BigDecimal valor) {
        this.racha = racha;
        this.devedor = devedor;
        this.credor = credor;
        this.valor = valor;
    }

    // --- Getters and Setters ---

    public Long getIdPagamento() {
        return idPagamento;
    }

    public void setIdPagamento(Long idPagamento) {
        this.idPagamento = idPagamento;
    }

    public Racha getRacha() {
        return racha;
    }

    public void setRacha(Racha racha) {
        this.racha = racha;
    }

    public Usuario getDevedor() {
        return devedor;
    }

    public void setDevedor(Usuario devedor) {
        this.devedor = devedor;
    }

    public Usuario getCredor() {
        return credor;
    }

    public void setCredor(Usuario credor) {
        this.credor = credor;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }
}