package com.rachapp.app.model;

import jakarta.persistence.*;

@Entity
@Table(name = "amigos")
@IdClass(AmigosId.class) // Must match the class name below
public class Amigos {

    @Id
    @Column(name = "id_usuario") // Maps 'idUsuario' (Java) to 'id_usuario' (DB)
    private Long idUsuario;

    @Id
    @Column(name = "id_amigo")   // Maps 'idAmigo' (Java) to 'id_amigo' (DB)
    private Long idAmigo;

    // --- Constructors ---
    public Amigos() {
    }

    public Amigos(Long idUsuario, Long idAmigo) {
        this.idUsuario = idUsuario;
        this.idAmigo = idAmigo;
    }

    // --- Getters and Setters (Standard Java Naming) ---

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
}