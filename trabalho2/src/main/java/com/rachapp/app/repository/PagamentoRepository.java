package com.rachapp.app.repository;

import com.rachapp.app.model.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {

    List<Pagamento> findByRachaIdRacha(Long idRacha);
    List<Pagamento> findByDevedorIdUsuario(Long idUsuario);
    List<Pagamento> findByCredorIdUsuario(Long idUsuario);

    @Query("SELECT COALESCE(SUM(p.valor), 0) FROM Pagamento p " +
            "WHERE p.racha.idRacha = :rachaId " +
            "AND p.devedor.idUsuario = :devedorId " +
            "AND p.credor.idUsuario = :credorId")
    BigDecimal calcularTotalPago(@Param("rachaId") Long rachaId,
                                 @Param("devedorId") Long devedorId,
                                 @Param("credorId") Long credorId);
}