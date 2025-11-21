package com.rachapp.app.model;

import java.io.Serializable;
import java.util.Objects;

public class CredorId implements Serializable {

    // Must match field names in Credor entity
    private Long idRacha;
    private Long idUsuario;

    public CredorId() {}

    public CredorId(Long idRacha, Long idUsuario) {
        this.idRacha = idRacha;
        this.idUsuario = idUsuario;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CredorId credorId = (CredorId) o;
        return Objects.equals(idRacha, credorId.idRacha) &&
                Objects.equals(idUsuario, credorId.idUsuario);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idRacha, idUsuario);
    }
}