package com.rachapp.app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "itens_racha")
public class ItemRacha {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_item_racha")
    private Long idItemRacha;

    @ManyToOne
    @JoinColumn(name = "id_racha", nullable = false)
    @JsonIgnore
    private Racha racha;

    @ManyToOne
    @JoinColumn(name = "payer_id")
    private Usuario payer;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private BigDecimal preco;

    public ItemRacha() {}

    public ItemRacha(Racha racha, Usuario payer, String nome, BigDecimal preco) {
        this.racha = racha;
        this.payer = payer;
        this.nome = nome;
        this.preco = preco;
    }

    // --- Getters and Setters ---
    public Long getIdItemRacha() { return idItemRacha; }
    public void setIdItemRacha(Long idItemRacha) { this.idItemRacha = idItemRacha; }
    public Racha getRacha() { return racha; }
    public void setRacha(Racha racha) { this.racha = racha; }
    public Usuario getPayer() { return payer; }
    public void setPayer(Usuario payer) { this.payer = payer; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public BigDecimal getPreco() { return preco; }
    public void setPreco(BigDecimal preco) { this.preco = preco; }
}