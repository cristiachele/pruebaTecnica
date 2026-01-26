package ec.com.banco.proyectobancario.application.usecase;

import java.util.List;

import ec.com.banco.proyectobancario.domain.model.Cliente;
import ec.com.banco.proyectobancario.domain.port.output.ClienteRepositoryPort;

/**
 * Caso de uso para la gestión de clientes.
 * Contiene la lógica de negocio para las operaciones CRUD de clientes.
 * 
 */
public class ClienteUseCase {
    
    private final ClienteRepositoryPort clienteRepository;
    
    public ClienteUseCase(ClienteRepositoryPort clienteRepository) {
        this.clienteRepository = clienteRepository;
    }
    
    /**
     * Crea un nuevo cliente.
     * 
     * @param cliente Cliente a crear
     * @return Cliente creado
     * @throws IllegalArgumentException si el clienteId ya existe
     */
    public Cliente crearCliente(Cliente cliente) {
        if (clienteRepository.existsByClienteId(cliente.getClienteId())) {
            throw new IllegalArgumentException("El clienteId ya existe: " + cliente.getClienteId());
        }
        return clienteRepository.save(cliente);
    }
    
    /**
     * Obtiene un cliente por su identificador.
     * 
     * @param id Identificador del cliente
     * @return Cliente encontrado
     * @throws IllegalArgumentException si el cliente no existe
     */
    public Cliente obtenerClientePorId(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado con id: " + id));
    }
    
    /**
     * Obtiene un cliente por su clienteId único.
     * 
     * @param clienteId Identificador único del cliente
     * @return Cliente encontrado
     * @throws IllegalArgumentException si el cliente no existe
     */
    public Cliente obtenerClientePorClienteId(String clienteId) {
        return clienteRepository.findByClienteId(clienteId)
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado con clienteId: " + clienteId));
    }
    
    /**
     * Obtiene todos los clientes.
     * 
     * @return Lista de clientes
     */
    public List<Cliente> obtenerTodosLosClientes() {
        return clienteRepository.findAll();
    }
    
    /**
     * Actualiza un cliente existente.
     * 
     * @param id Identificador del cliente
     * @param clienteActualizado Datos actualizados del cliente
     * @return Cliente actualizado
     * @throws IllegalArgumentException si el cliente no existe
     */
    public Cliente actualizarCliente(Long id, Cliente clienteActualizado) {
        Cliente clienteExistente = obtenerClientePorId(id);
        
        // Verificar si se está cambiando el clienteId y si ya existe
        if (!clienteExistente.getClienteId().equals(clienteActualizado.getClienteId()) 
                && clienteRepository.existsByClienteId(clienteActualizado.getClienteId())) {
            throw new IllegalArgumentException("El clienteId ya existe: " + clienteActualizado.getClienteId());
        }
        
        clienteActualizado.setId(id);
        return clienteRepository.save(clienteActualizado);
    }
    
    /**
     * Elimina un cliente por su identificador.
     * 
     * @param id Identificador del cliente
     * @throws IllegalArgumentException si el cliente no existe
     */
    public void eliminarCliente(Long id) {
        obtenerClientePorId(id); // Verifica que existe
        clienteRepository.deleteById(id);
    }
}
