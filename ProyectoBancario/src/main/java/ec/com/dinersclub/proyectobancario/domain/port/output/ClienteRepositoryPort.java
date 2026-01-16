package ec.com.dinersclub.proyectobancario.domain.port.output;

import ec.com.dinersclub.proyectobancario.domain.model.Cliente;

import java.util.List;
import java.util.Optional;

/**
 * Puerto de salida para la gestión de clientes.
 * Define las operaciones de persistencia para la entidad Cliente.
 * 
 * @author Sistema Bancario
 * @version 1.0.0
 */
public interface ClienteRepositoryPort {
    
    /**
     * Guarda un cliente en el repositorio.
     * 
     * @param cliente Cliente a guardar
     * @return Cliente guardado
     */
    Cliente save(Cliente cliente);
    
    /**
     * Busca un cliente por su identificador único.
     * 
     * @param id Identificador del cliente
     * @return Cliente encontrado o vacío
     */
    Optional<Cliente> findById(Long id);
    
    /**
     * Busca un cliente por su clienteId único.
     * 
     * @param clienteId Identificador único del cliente
     * @return Cliente encontrado o vacío
     */
    Optional<Cliente> findByClienteId(String clienteId);
    
    /**
     * Obtiene todos los clientes.
     * 
     * @return Lista de clientes
     */
    List<Cliente> findAll();
    
    /**
     * Elimina un cliente por su identificador.
     * 
     * @param id Identificador del cliente
     */
    void deleteById(Long id);
    
    /**
     * Verifica si existe un cliente con el clienteId dado.
     * 
     * @param clienteId Identificador único del cliente
     * @return true si existe, false en caso contrario
     */
    boolean existsByClienteId(String clienteId);
}
