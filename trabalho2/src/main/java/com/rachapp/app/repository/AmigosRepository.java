package com.rachapp.app.repository;

import com.rachapp.app.model.Amigos;
import com.rachapp.app.model.AmigosId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AmigosRepository extends JpaRepository<Amigos, AmigosId> {

}