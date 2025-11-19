package com.rachapp.app.model;

import java.io.Serializable;
import java.util.Objects;

public class AmigosId implements Serializable {
    
    private Long id_usuario;
    private Long id_amigo;

    // Construtor Vazio (Obrigatório)
    public AmigosId() {}

    // Construtor com argumentos (Facilita sua vida depois)
    public AmigosId(Long id_usuario, Long id_amigo) {
        this.id_usuario = id_usuario;
        this.id_amigo = id_amigo;
    }

    public Long get_id_usuario() {return id_usuario;}
    public void set_id_usuario(Long id_usuario) {this.id_usuario = id_usuario;}

    public Long get_id_amigo() {return id_amigo;}
    public void set_id_amigo(Long id_amigo) {this.id_amigo = id_amigo;}    

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AmigosId that = (AmigosId) o;
        return Objects.equals(id_usuario, that.id_usuario) && 
               Objects.equals(id_amigo, that.id_amigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_usuario, id_amigo);
    }
}