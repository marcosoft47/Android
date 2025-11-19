package com.rachapp.app.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.time.LocalDateTime;
import org.springframework.data.geo.Point;

@Entity
public class Racha {
    
    @Id
    private Long id_racha;
    private String nome;
    private LocalDateTime timestamp;
    private Point location;

    public String get_nome() {return nome;}
    public void set_nome(String nome) {this.nome = nome;}

    public LocalDateTime get_timestamp() {return timestamp;}
    public void set_timestamp(LocalDateTime timestamp) {this.timestamp = timestamp;}

    public Point get_location() {return location;}
    public void set_location(Point timestamp) {this.location = location;}

}