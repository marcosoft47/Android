package com.rachapp.app.repository;

import com.rachapp.app.model.Devedor;
import com.rachapp.app.model.DevedorId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DevedorRepository extends JpaRepository<Devedor, DevedorId> {

    // Find everyone responsible for a specific Item
    List<Devedor> findByIdItemRacha(Long idItemRacha);

    // Find all items a specific User is responsible for
    List<Devedor> findByIdUsuario(Long idUsuario);
}