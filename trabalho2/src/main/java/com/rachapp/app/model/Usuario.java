package com.rachapp.app.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;

@Entity
@Table(name = "usuarios")
public class Usuario {
    
    @Id
    private Long id_usuario;
    private String nome;
    private String email;
    private String telefone;

    public String get_nome() {return nome;}
    public void set_nome(String nome) {this.nome = nome;}

    public String get_email() {return email;}
    public void set_email(String email) {this.email = email;}

    public String get_telefone() {return telefone;}
    public void set_telefone(String email) {this.email = email;}
    
}