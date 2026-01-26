package ec.com.banco.proyectobancario.adapter.input.web.mapper;

import ec.com.banco.proyectobancario.adapter.input.web.dto.ClienteRequest;
import ec.com.banco.proyectobancario.adapter.input.web.dto.ClienteResponse;
import ec.com.banco.proyectobancario.domain.model.Cliente;

import org.springframework.stereotype.Component;

/**
 * Mapper para convertir entre DTOs y modelos de dominio de Cliente.
 * 
 */
@Component("clienteWebMapper")
public class ClienteMapper {
    
    /**
     * Convierte un ClienteRequest a Cliente del dominio.
     * 
     * @param request DTO de request
     * @return Modelo de dominio
     */
    public Cliente toDomain(ClienteRequest request) {
        if (request == null) {
            return null;
        }
        
        Cliente cliente = new Cliente();
        cliente.setNombre(request.getNombre());
        cliente.setGenero(request.getGenero());
        cliente.setEdad(request.getEdad());
        cliente.setIdentificacion(request.getIdentificacion());
        cliente.setDireccion(request.getDireccion());
        cliente.setTelefono(request.getTelefono());
        cliente.setClienteId(request.getClienteId());
        cliente.setContrasena(request.getContrasena());
        cliente.setEstado(request.getEstado());
        
        return cliente;
    }
    
    /**
     * Convierte un Cliente del dominio a ClienteResponse.
     * 
     * @param cliente Modelo de dominio
     * @return DTO de response
     */
    public ClienteResponse toResponse(Cliente cliente) {
        if (cliente == null) {
            return null;
        }
        
        ClienteResponse response = new ClienteResponse();
        response.setId(cliente.getId());
        response.setNombre(cliente.getNombre());
        response.setGenero(cliente.getGenero());
        response.setEdad(cliente.getEdad());
        response.setIdentificacion(cliente.getIdentificacion());
        response.setDireccion(cliente.getDireccion());
        response.setTelefono(cliente.getTelefono());
        response.setClienteId(cliente.getClienteId());
        response.setEstado(cliente.getEstado());
        
        return response;
    }
}
