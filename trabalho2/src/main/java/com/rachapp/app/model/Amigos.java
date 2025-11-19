package com.rachapp.app.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.IdClass;

@Entity
@Table(name = "amigos")
@IdClass(AmigosId.class)
public class Amigos {
    
    @Id
    private Long id_usuario;
    
    @Id
    private Long id_amigo;

    public Long get_id_usuario() {return id_usuario;}
    public void set_id_usuario(Long id_usuario) {this.id_usuario = id_usuario;}

    public Long get_id_amigo() {return id_amigo;}
    public void set_id_amigo(Long id_amigo) {this.id_amigo = id_amigo;}    
}