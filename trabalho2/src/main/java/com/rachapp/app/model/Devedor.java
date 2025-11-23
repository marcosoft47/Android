package com.rachapp.app.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "devedores")
@IdClass(DevedorId.class)
public class Devedor {

    @Id
    @Column(name = "id_item_racha")
    private Long idItemRacha;

    @Id
    @Column(name = "id_usuario")
    private Long idUsuario;

    // Updated to BigDecimal for precision (e.g. 33.33%)
    @Column(name = "percentual", precision = 5, scale = 2)
    private BigDecimal percentual;

    // --- Constructors ---
    public Devedor() {}

    public Devedor(Long idItemRacha, Long idUsuario, BigDecimal percentual) {
        this.idItemRacha = idItemRacha;
        this.idUsuario = idUsuario;
        this.percentual = percentual;
    }

    // --- Getters and Setters ---

    public Long getIdItemRacha() { return idItemRacha; }
    public void setIdItemRacha(Long idItemRacha) { this.idItemRacha = idItemRacha; }

    public Long getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Long idUsuario) { this.idUsuario = idUsuario; }

    public BigDecimal getPercentual() { return percentual; }
    public void setPercentual(BigDecimal percentual) { this.percentual = percentual; }
}