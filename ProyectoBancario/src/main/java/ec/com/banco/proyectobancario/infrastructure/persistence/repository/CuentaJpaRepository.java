package ec.com.banco.proyectobancario.infrastructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ec.com.banco.proyectobancario.infrastructure.persistence.entity.CuentaEntity;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio JPA para la entidad Cuenta.
 * 
 */
@Repository
public interface CuentaJpaRepository extends JpaRepository<CuentaEntity, Long> {
    
    /**
     * Busca una cuenta por su número de cuenta único.
     * 
     * @param numeroCuenta Número de cuenta
     * @return Cuenta encontrada o vacío
     */
    Optional<CuentaEntity> findByNumeroCuenta(String numeroCuenta);
    
    /**
     * Obtiene todas las cuentas de un cliente.
     * 
     * @param clienteId Identificador del cliente
     * @return Lista de cuentas del cliente
     */
    List<CuentaEntity> findByClienteId(Long clienteId);
    
    /**
     * Verifica si existe una cuenta con el número de cuenta dado.
     * 
     * @param numeroCuenta Número de cuenta
     * @return true si existe, false en caso contrario
     */
    boolean existsByNumeroCuenta(String numeroCuenta);
}
