package com.rachapp.app.model;

import java.io.Serializable;
import java.util.Objects;

public class AmigosId implements Serializable {

    // FIELD NAMES MUST MATCH THE ENTITY EXACTLY
    private Long idUsuario;
    private Long idAmigo;

    public AmigosId() {}

    public AmigosId(Long idUsuario, Long idAmigo) {
        this.idUsuario = idUsuario;
        this.idAmigo = idAmigo;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Long getIdAmigo() {
        return idAmigo;
    }

    public void setIdAmigo(Long idAmigo) {
        this.idAmigo = idAmigo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AmigosId amigosId = (AmigosId) o;
        return Objects.equals(idUsuario, amigosId.idUsuario) &&
                Objects.equals(idAmigo, amigosId.idAmigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idUsuario, idAmigo);
    }
}