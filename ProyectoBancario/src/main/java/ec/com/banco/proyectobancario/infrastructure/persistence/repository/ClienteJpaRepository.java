package ec.com.banco.proyectobancario.infrastructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ec.com.banco.proyectobancario.infrastructure.persistence.entity.ClienteEntity;

import java.util.Optional;

/**
 * Repositorio JPA para la entidad Cliente.
 * 
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
