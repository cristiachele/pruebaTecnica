package ec.com.dinersclub.proyectobancario.infrastructure.persistence.mapper;

import org.springframework.stereotype.Component;

import ec.com.dinersclub.proyectobancario.domain.model.Cliente;
import ec.com.dinersclub.proyectobancario.infrastructure.persistence.entity.ClienteEntity;

/**
 * Mapper para convertir entre Cliente (dominio) y ClienteEntity (infraestructura).
 * 
 * @author Sistema Bancario
 * @version 1.0.0
 */
@Component("clientePersistenceMapper")
public class ClienteMapper {
    
    /**
     * Convierte una ClienteEntity a Cliente del dominio.
     * 
     * @param entity Entidad JPA
     * @return Modelo de dominio
     */
    public Cliente toDomain(ClienteEntity entity) {
        if (entity == null) {
            return null;
        }
        
        Cliente cliente = new Cliente();
        cliente.setId(entity.getId());
        cliente.setNombre(entity.getNombre());
        cliente.setGenero(entity.getGenero());
        cliente.setEdad(entity.getEdad());
        cliente.setIdentificacion(entity.getIdentificacion());
        cliente.setDireccion(entity.getDireccion());
        cliente.setTelefono(entity.getTelefono());
        cliente.setClienteId(entity.getClienteId());
        cliente.setContrasena(entity.getContrasena());
        cliente.setEstado(entity.getEstado());
        
        return cliente;
    }
    
    /**
     * Convierte un Cliente del dominio a ClienteEntity.
     * 
     * @param cliente Modelo de dominio
     * @return Entidad JPA
     */
    public ClienteEntity toEntity(Cliente cliente) {
        if (cliente == null) {
            return null;
        }
        
        ClienteEntity entity = new ClienteEntity();
        entity.setId(cliente.getId());
        entity.setNombre(cliente.getNombre());
        entity.setGenero(cliente.getGenero());
        entity.setEdad(cliente.getEdad());
        entity.setIdentificacion(cliente.getIdentificacion());
        entity.setDireccion(cliente.getDireccion());
        entity.setTelefono(cliente.getTelefono());
        entity.setClienteId(cliente.getClienteId());
        entity.setContrasena(cliente.getContrasena());
        entity.setEstado(cliente.getEstado());
        
        return entity;
    }
}
