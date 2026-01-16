package ec.com.dinersclub.proyectobancario.infrastructure.persistence.repository;

import ec.com.dinersclub.proyectobancario.infrastructure.persistence.entity.ClienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositorio JPA para la entidad Cliente.
 * 
 * @author Sistema Bancario
 * @version 1.0.0
 */
@Repository
public interface ClienteJpaRepository extends JpaRepository<ClienteEntity, Long> {
    
    /**
     * Busca un cliente por su clienteId único.
     * 
     * @param clienteId Identificador único del cliente
     * @return Cliente encontrado o vacío
     */
    Optional<ClienteEntity> findByClienteId(String clienteId);
    
    /**
     * Verifica si existe un cliente con el clienteId dado.
     * 
     * @param clienteId Identificador único del cliente
     * @return true si existe, false en caso contrario
     */
    boolean existsByClienteId(String clienteId);
}
