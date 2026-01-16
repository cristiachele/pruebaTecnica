package ec.com.dinersclub.proyectobancario.infrastructure.persistence.repository;

import ec.com.dinersclub.proyectobancario.infrastructure.persistence.entity.MovimientoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repositorio JPA para la entidad Movimiento.
 * 
 * @author Sistema Bancario
 * @version 1.0.0
 */
@Repository
public interface MovimientoJpaRepository extends JpaRepository<MovimientoEntity, Long> {
    
    /**
     * Obtiene todos los movimientos de una cuenta.
     * 
     * @param cuentaId Identificador de la cuenta
     * @return Lista de movimientos de la cuenta
     */
    List<MovimientoEntity> findByCuentaId(Long cuentaId);
    
    /**
     * Obtiene movimientos de una cuenta en un rango de fechas.
     * 
     * @param cuentaId Identificador de la cuenta
     * @param fechaInicio Fecha de inicio del rango
     * @param fechaFin Fecha de fin del rango
     * @return Lista de movimientos en el rango de fechas
     */
    List<MovimientoEntity> findByCuentaIdAndFechaBetween(Long cuentaId, LocalDateTime fechaInicio, LocalDateTime fechaFin);
    
    /**
     * Obtiene movimientos de débito de una cuenta en una fecha específica.
     * 
     * @param cuentaId Identificador de la cuenta
     * @param fechaInicio Inicio del día
     * @param fechaFin Fin del día
     * @return Lista de movimientos de débito del día
     */
    @Query("SELECT m FROM MovimientoEntity m WHERE m.cuentaId = :cuentaId " +
           "AND m.tipoMovimiento = 'Débito' " +
           "AND m.fecha >= :fechaInicio AND m.fecha <= :fechaFin")
    List<MovimientoEntity> findDebitosByCuentaIdAndFechaBetween(
            @Param("cuentaId") Long cuentaId,
            @Param("fechaInicio") LocalDateTime fechaInicio,
            @Param("fechaFin") LocalDateTime fechaFin);
}
