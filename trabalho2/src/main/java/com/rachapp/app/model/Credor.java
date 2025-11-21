package com.rachapp.app.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "credores")
@IdClass(CredorId.class) // Links to the composite key class
public class Credor {

    @Id
    @Column(name = "id_racha")
    private Long idRacha;

    @Id
    @Column(name = "id_usuario")
    private Long idUsuario;

    @Column(name = "valor_pago", nullable = false)
    private BigDecimal valorPago;

    // --- Constructors ---
    public Credor() {}

    public Credor(Long idRacha, Long idUsuario, BigDecimal valorPago) {
        this.idRacha = idRacha;
        this.idUsuario = idUsuario;
        this.valorPago = valorPago;
    }

    // --- Getters and Setters ---

    public Long getIdRacha() {
        return idRacha;
    }

    public void setIdRacha(Long idRacha) {
        this.idRacha = idRacha;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public BigDecimal getValorPago() {
        return valorPago;
    }

    public void setValorPago(BigDecimal valorPago) {
        this.valorPago = valorPago;
    }
}