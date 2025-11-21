package com.rachapp.app.model;

import jakarta.persistence.*;

@Entity
@Table(name = "devedores")
@IdClass(DevedorId.class) // Links to the composite key class
public class Devedor {

    @Id
    @Column(name = "id_item_racha")
    private Long idItemRacha;

    @Id
    @Column(name = "id_usuario")
    private Long idUsuario;

    @Column(name = "percentual")
    private Integer percentual;

    // --- Constructors ---
    public Devedor() {}

    public Devedor(Long idItemRacha, Long idUsuario, Integer percentual) {
        this.idItemRacha = idItemRacha;
        this.idUsuario = idUsuario;
        this.percentual = percentual;
    }

    // --- Getters and Setters ---

    public Long getIdItemRacha() {
        return idItemRacha;
    }

    public void setIdItemRacha(Long idItemRacha) {
        this.idItemRacha = idItemRacha;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Integer getPercentual() {
        return percentual;
    }

    public void setPercentual(Integer percentual) {
        this.percentual = percentual;
    }
}