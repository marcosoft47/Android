package com.rachapp.app.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import com.fasterxml.jackson.annotation.JsonProperty;
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

    @CreationTimestamp
    @Column(name = "criado_em", nullable = false, updatable = false)
    private LocalDateTime criadoEm;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "ENUM('ABERTO', 'FECHADO') DEFAULT 'ABERTO'")
    private RachaStatus status;

    @Column(name = "local_nome")
    private String localNome;

    @Column(columnDefinition = "POINT")
    private Point location;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Usuario owner;

    @OneToMany(mappedBy = "racha", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemRacha> itens;

    @Transient
    private Double latitude;

    @Transient
    private Double longitude;

    // FIXED: Added this field to capture the ID from JSON
    @Transient
    private Long ownerId;

    public Racha() {}

    public Racha(String nome, Double latitude, Double longitude, String localNome, Usuario owner) {
        this.nome = nome;
        this.localNome = localNome;
        this.status = RachaStatus.ABERTO;
        this.latitude = latitude;
        this.longitude = longitude;
        this.owner = owner;
        updateLocationFromCoordinates();
    }

    @PrePersist
    @PreUpdate
    private void updateLocationFromCoordinates() {
        if (latitude != null && longitude != null) {
            GeometryFactory geometryFactory = new GeometryFactory();
            this.location = geometryFactory.createPoint(new Coordinate(longitude, latitude));
        }
    }

    // --- Getters and Setters ---
    public Long getIdRacha() { return idRacha; }
    public void setIdRacha(Long idRacha) { this.idRacha = idRacha; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public LocalDateTime getCriadoEm() { return criadoEm; }
    public void setCriadoEm(LocalDateTime criadoEm) { this.criadoEm = criadoEm; }
    public RachaStatus getStatus() { return status; }
    public void setStatus(RachaStatus status) { this.status = status; }
    public String getLocalNome() { return localNome; }
    public void setLocalNome(String localNome) { this.localNome = localNome; }
    public Usuario getOwner() { return owner; }
    public void setOwner(Usuario owner) { this.owner = owner; }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
        updateLocationFromCoordinates();
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
        updateLocationFromCoordinates();
    }

    public Long getOwnerId() { return ownerId; }
    public void setOwnerId(Long ownerId) { this.ownerId = ownerId; }

    @JsonProperty
    public Double getLatitude() {
        if (location != null) return location.getY();
        return latitude;
    }

    @JsonProperty
    public Double getLongitude() {
        if (location != null) return location.getX();
        return longitude;
    }

    public List<ItemRacha> getItens() { return itens; }
    public void setItens(List<ItemRacha> itens) { this.itens = itens; }
}