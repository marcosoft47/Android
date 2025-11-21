package com.rachapp.app.model;

import jakarta.persistence.*;
import org.locationtech.jts.geom.Point;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "rachas")
public class Racha {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_racha")
    private Long idRacha;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    // Geometry type for MySQL Spatial
    @Column(columnDefinition = "POINT")
    private Point location;

    // Relationship: One Racha has Many Items
    // "mappedBy" refers to the 'racha' field in ItemRacha.java
    // Cascade ALL means if you delete the Racha, all Items are deleted too.
    @OneToMany(mappedBy = "racha", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemRacha> itens;

    // --- Constructors ---
    public Racha() {}

    public Racha(String nome, LocalDateTime timestamp, Point location) {
        this.nome = nome;
        this.timestamp = timestamp;
        this.location = location;
    }

    // --- Getters and Setters ---

    public Long getIdRacha() {
        return idRacha;
    }

    public void setIdRacha(Long idRacha) {
        this.idRacha = idRacha;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Point getLocation() {
        return location;
    }

    public void setLocation(Point location) {
        this.location = location;
    }

    public List<ItemRacha> getItens() {
        return itens;
    }

    public void setItens(List<ItemRacha> itens) {
        this.itens = itens;
    }
}