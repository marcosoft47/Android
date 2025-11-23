package com.rachapp.app.dto;

import java.math.BigDecimal;
import java.util.List;

public class ItemCreationDTO {
    private String nome;
    private BigDecimal preco;
    private Long rachaId;
    private Long payerId;
    private List<Long> participantesIds;

    // Getters and Setters
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public BigDecimal getPreco() { return preco; }
    public void setPreco(BigDecimal preco) { this.preco = preco; }
    public Long getRachaId() { return rachaId; }
    public void setRachaId(Long rachaId) { this.rachaId = rachaId; }
    public Long getPayerId() { return payerId; }
    public void setPayerId(Long payerId) { this.payerId = payerId; }
    public List<Long> getParticipantesIds() { return participantesIds; }
    public void setParticipantesIds(List<Long> participantesIds) { this.participantesIds = participantesIds; }
}