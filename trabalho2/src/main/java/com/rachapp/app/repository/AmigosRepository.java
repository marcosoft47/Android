package com.rachapp.app.repository;

import com.rachapp.app.model.Amigos;
import com.rachapp.app.model.AmigosId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AmigosRepository extends JpaRepository<Amigos, AmigosId> {

    // Custom method to find all rows where the user is the "owner" of the friendship
    List<Amigos> findByIdUsuario(Long idUsuario);

    // Optional: Check if a friendship exists
    boolean existsByIdUsuarioAndIdAmigo(Long idUsuario, Long idAmigo);
}