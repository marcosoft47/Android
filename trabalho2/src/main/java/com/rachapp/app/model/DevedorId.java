package com.rachapp.app.model;

import java.io.Serializable;
import java.util.Objects;

public class DevedorId implements Serializable {

    // Must match field names in Devedor entity exactly
    private Long idItemRacha;
    private Long idUsuario;

    public DevedorId() {}

    public DevedorId(Long idItemRacha, Long idUsuario) {
        this.idItemRacha = idItemRacha;
        this.idUsuario = idUsuario;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DevedorId devedorId = (DevedorId) o;
        return Objects.equals(idItemRacha, devedorId.idItemRacha) &&
                Objects.equals(idUsuario, devedorId.idUsuario);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idItemRacha, idUsuario);
    }
}