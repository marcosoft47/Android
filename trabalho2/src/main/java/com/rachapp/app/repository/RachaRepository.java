package com.rachapp.app.repository;

import com.rachapp.app.model.Racha;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RachaRepository extends JpaRepository<Racha, Long> {
    // Custom queries can be added here later
    // e.g., List<Racha> findByNomeContaining(String text);
}