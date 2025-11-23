package com.rachapp.app.dto;

import com.rachapp.app.model.ItemRacha;
import java.math.BigDecimal;
import java.util.List;

public class BalanceDTO {
    private Long userId;
    private String nome;
    private Integer avatarId;
    private BigDecimal totalPago;      // Paid by them
    private BigDecimal totalConsumido; // Consumed by them
    private BigDecimal saldo;          // Paid - Consumed (Positive = Receive, Negative = Owe)
    private List<ItemRacha> itensConsumidos; // Details for the dialog

    public BalanceDTO(Long userId, String nome, Integer avatarId, BigDecimal totalPago, BigDecimal totalConsumido, List<ItemRacha> itensConsumidos) {
        this.userId = userId;
        this.nome = nome;
        this.avatarId = avatarId;
        this.totalPago = totalPago;
        this.totalConsumido = totalConsumido;
        this.saldo = totalPago.subtract(totalConsumido);
        this.itensConsumidos = itensConsumidos;
    }

    // Getters & Setters
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public Integer getAvatarId() { return avatarId; }
    public void setAvatarId(Integer avatarId) { this.avatarId = avatarId; }
    public BigDecimal getTotalPago() { return totalPago; }
    public void setTotalPago(BigDecimal totalPago) { this.totalPago = totalPago; }
    public BigDecimal getTotalConsumido() { return totalConsumido; }
    public void setTotalConsumido(BigDecimal totalConsumido) { this.totalConsumido = totalConsumido; }
    public BigDecimal getSaldo() { return saldo; }
    public void setSaldo(BigDecimal saldo) { this.saldo = saldo; }
    public List<ItemRacha> getItensConsumidos() { return itensConsumidos; }
    public void setItensConsumidos(List<ItemRacha> itensConsumidos) { this.itensConsumidos = itensConsumidos; }
}