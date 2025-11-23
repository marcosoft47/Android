package com.rachapp.app.dto;

import java.math.BigDecimal;

public class ResumoItemDTO {
    private String nomePessoa;   // The person you owe or who owes you
    private String nomeRacha;    // Context (e.g., "Churrasco")
    private BigDecimal valor;
    private Integer avatarId;    // Avatar of the other person

    public ResumoItemDTO(String nomePessoa, String nomeRacha, BigDecimal valor, Integer avatarId) {
        this.nomePessoa = nomePessoa;
        this.nomeRacha = nomeRacha;
        this.valor = valor;
        this.avatarId = avatarId;
    }

    // Getters and Setters
    public String getNomePessoa() { return nomePessoa; }
    public void setNomePessoa(String nomePessoa) { this.nomePessoa = nomePessoa; }
    public String getNomeRacha() { return nomeRacha; }
    public void setNomeRacha(String nomeRacha) { this.nomeRacha = nomeRacha; }
    public BigDecimal getValor() { return valor; }
    public void setValor(BigDecimal valor) { this.valor = valor; }
    public Integer getAvatarId() { return avatarId; }
    public void setAvatarId(Integer avatarId) { this.avatarId = avatarId; }
}