package com.rachapp.app.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "itens_racha")
public class ItemRacha {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_item_racha")
    private Long idItemRacha;

    // Relationship: An item belongs to a specific Racha
    @ManyToOne
    @JoinColumn(name = "id_racha", nullable = false)
    private Racha racha;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private BigDecimal preco;

    // --- Constructors ---
    public ItemRacha() {}

    public ItemRacha(Racha racha, String nome, BigDecimal preco) {
        this.racha = racha;
        this.nome = nome;
        this.preco = preco;
    }

    // --- Getters and Setters ---

    public Long getIdItemRacha() {
        return idItemRacha;
    }

    public void setIdItemRacha(Long idItemRacha) {
        this.idItemRacha = idItemRacha;
    }

    public Racha getRacha() {
        return racha;
    }

    public void setRacha(Racha racha) {
        this.racha = racha;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }
}