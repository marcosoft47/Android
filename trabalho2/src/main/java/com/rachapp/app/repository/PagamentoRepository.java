package com.rachapp.app.repository;

import com.rachapp.app.model.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {

    // Find all payments for a specific Racha
    List<Pagamento> findByRachaIdRacha(Long idRacha);

    // Find payments made BY a specific user (Devedor)
    List<Pagamento> findByDevedorIdUsuario(Long idUsuario);

    // Find payments received BY a specific user (Credor)
    List<Pagamento> findByCredorIdUsuario(Long idUsuario);
}