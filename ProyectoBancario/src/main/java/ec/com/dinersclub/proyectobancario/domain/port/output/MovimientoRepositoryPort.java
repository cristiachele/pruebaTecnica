package ec.com.dinersclub.proyectobancario.domain.port.output;

import ec.com.dinersclub.proyectobancario.domain.model.Movimiento;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Puerto de salida para la gestión de movimientos.
 * Define las operaciones de persistencia para la entidad Movimiento.
 * 
 * @author Sistema Bancario
 * @version 1.0.0
 */
public interface MovimientoRepositoryPort {
    
    /**
     * Guarda un movimiento en el repositorio.
     * 
     * @param movimiento Movimiento a guardar
     * @return Movimiento guardado
     */
    Movimiento save(Movimiento movimiento);
    
    /**
     * Busca un movimiento por su identificador único.
     * 
     * @param id Identificador del movimiento
     * @return Movimiento encontrado o vacío
     */
    Optional<Movimiento> findById(Long id);
    
    /**
     * Obtiene todos los movimientos de una cuenta.
     * 
     * @param cuentaId Identificador de la cuenta
     * @return Lista de movimientos de la cuenta
     */
    List<Movimiento> findByCuentaId(Long cuentaId);
    
    /**
     * Obtiene movimientos de una cuenta en un rango de fechas.
     * 
     * @param cuentaId Identificador de la cuenta
     * @param fechaInicio Fecha de inicio del rango
     * @param fechaFin Fecha de fin del rango
     * @return Lista de movimientos en el rango de fechas
     */
    List<Movimiento> findByCuentaIdAndFechaBetween(Long cuentaId, LocalDateTime fechaInicio, LocalDateTime fechaFin);
    
    /**
     * Obtiene movimientos de débito de una cuenta en una fecha específica.
     * 
     * @param cuentaId Identificador de la cuenta
     * @param fecha Fecha del movimiento
     * @return Lista de movimientos de débito del día
     */
    List<Movimiento> findDebitosByCuentaIdAndFecha(Long cuentaId, LocalDateTime fecha);
    
    /**
     * Obtiene todos los movimientos.
     * 
     * @return Lista de movimientos
     */
    List<Movimiento> findAll();
    
    /**
     * Elimina un movimiento por su identificador.
     * 
     * @param id Identificador del movimiento
     */
    void deleteById(Long id);
}
