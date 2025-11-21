package com.rachapp.app.repository;

import com.rachapp.app.model.Credor;
import com.rachapp.app.model.CredorId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CredorRepository extends JpaRepository<Credor, CredorId> {

    // Find all people who paid for a specific Racha
    List<Credor> findByIdRacha(Long idRacha);

    // Find all payments made by a specific User
    List<Credor> findByIdUsuario(Long idUsuario);
}