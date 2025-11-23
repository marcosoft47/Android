package com.rachapp.app.repository;

import com.rachapp.app.model.Racha;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RachaRepository extends JpaRepository<Racha, Long> {

    // Native Query is often cleaner for complex OR logic across join tables
    @Query(value = "SELECT DISTINCT r.* FROM rachas r " +
            "LEFT JOIN itens_racha i ON r.id_racha = i.id_racha " +
            "LEFT JOIN devedores d ON i.id_item_racha = d.id_item_racha " +
            "LEFT JOIN credores c ON r.id_racha = c.id_racha " +
            "WHERE r.owner_id = :userId " +
            "OR d.id_usuario = :userId " +
            "OR c.id_usuario = :userId", nativeQuery = true)
    List<Racha> findRachasByParticipante(@Param("userId") Long userId);
}