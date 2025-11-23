package com.rachapp.app.dto;

import java.math.BigDecimal;

public class ResumoItemDTO {
    private String nomePessoa;
    private String nomeRacha;
    private BigDecimal valor;
    private Integer avatarId;
    private Long userId;
    private Long rachaId;

    public ResumoItemDTO(String nomePessoa, String nomeRacha, BigDecimal valor, Integer avatarId, Long userId, Long rachaId) {
        this.nomePessoa = nomePessoa;
        this.nomeRacha = nomeRacha;
        this.valor = valor;
        this.avatarId = avatarId;
        this.userId = userId;
        this.rachaId = rachaId;
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
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public Long getRachaId() { return rachaId; }
    public void setRachaId(Long rachaId) { this.rachaId = rachaId; }
}