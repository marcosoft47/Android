package com.rachapp.app.repository;

import com.rachapp.app.model.ItemRacha;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRachaRepository extends JpaRepository<ItemRacha, Long> {

    // Find all items for a specific Racha ID
    // Spring automatically navigates: ItemRacha -> Racha -> idRacha
    List<ItemRacha> findByRachaIdRacha(Long idRacha);
}