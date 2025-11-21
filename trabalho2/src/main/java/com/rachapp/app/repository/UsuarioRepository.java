package com.rachapp.app.repository;

import com.rachapp.app.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    // You don't need to write code here!
    // Spring automatically gives you: save, findById, findAll, delete, etc.

    // If you need custom queries later, you can add them here, e.g.:
    // Optional<Usuario> findByEmail(String email);
}