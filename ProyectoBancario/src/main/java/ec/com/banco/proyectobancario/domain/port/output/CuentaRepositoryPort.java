package ec.com.banco.proyectobancario.domain.port.output;

import java.util.List;
import java.util.Optional;

import ec.com.banco.proyectobancario.domain.model.Cuenta;

/**
 * Puerto de salida para la gestión de cuentas.
 * Define las operaciones de persistencia para la entidad Cuenta.
 * 
 */
public interface CuentaRepositoryPort {
    
    /**
     * Guarda una cuenta en el repositorio.
     * 
     * @param cuenta Cuenta a guardar
     * @return Cuenta guardada
     */
    Cuenta save(Cuenta cuenta);
    
    /**
     * Busca una cuenta por su identificador único.
     * 
     * @param id Identificador de la cuenta
     * @return Cuenta encontrada o vacío
     */
    Optional<Cuenta> findById(Long id);
    
    /**
     * Busca una cuenta por su número de cuenta único.
     * 
     * @param numeroCuenta Número de cuenta
     * @return Cuenta encontrada o vacío
     */
    Optional<Cuenta> findByNumeroCuenta(String numeroCuenta);
    
    /**
     * Obtiene todas las cuentas de un cliente.
     * 
     * @param clienteId Identificador del cliente
     * @return Lista de cuentas del cliente
     */
    List<Cuenta> findByClienteId(Long clienteId);
    
    /**
     * Obtiene todas las cuentas.
     * 
     * @return Lista de cuentas
     */
    List<Cuenta> findAll();
    
    /**
     * Elimina una cuenta por su identificador.
     * 
     * @param id Identificador de la cuenta
     */
    void deleteById(Long id);
    
    /**
     * Verifica si existe una cuenta con el número de cuenta dado.
     * 
     * @param numeroCuenta Número de cuenta
     * @return true si existe, false en caso contrario
     */
    boolean existsByNumeroCuenta(String numeroCuenta);
}
