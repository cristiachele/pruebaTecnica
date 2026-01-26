package ec.com.banco.proyectobancario.adapter.input.web.controller;

import ec.com.banco.proyectobancario.adapter.input.web.dto.ClienteRequest;
import ec.com.banco.proyectobancario.adapter.input.web.dto.ClienteResponse;
import ec.com.banco.proyectobancario.adapter.input.web.mapper.ClienteMapper;
import ec.com.banco.proyectobancario.application.usecase.ClienteUseCase;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Controlador REST para la gestión de clientes.
 * 
 */
@RestController
@RequestMapping("/clientes")
@CrossOrigin(origins = "*")
public class ClienteController {
    
    private final ClienteUseCase clienteUseCase;
    private final ClienteMapper clienteMapper;
    
    public ClienteController(ClienteUseCase clienteUseCase, ClienteMapper clienteMapper) {
        this.clienteUseCase = clienteUseCase;
        this.clienteMapper = clienteMapper;
    }
    
    /**
     * Crea un nuevo cliente.
     * 
     * @param request Datos del cliente
     * @return Cliente creado
     */
    @PostMapping
    public ResponseEntity<ClienteResponse> crearCliente(@Valid @RequestBody ClienteRequest request) {
        var cliente = clienteUseCase.crearCliente(clienteMapper.toDomain(request));
        return new ResponseEntity<>(clienteMapper.toResponse(cliente), HttpStatus.CREATED);
    }
    
    /**
     * Obtiene un cliente por su identificador.
     * 
     * @param id Identificador del cliente
     * @return Cliente encontrado
     */
    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponse> obtenerCliente(@PathVariable Long id) {
        var cliente = clienteUseCase.obtenerClientePorId(id);
        return ResponseEntity.ok(clienteMapper.toResponse(cliente));
    }
    
    /**
     * Obtiene todos los clientes.
     * 
     * @return Lista de clientes
     */
    @GetMapping
    public ResponseEntity<List<ClienteResponse>> obtenerTodosLosClientes() {
        var clientes = clienteUseCase.obtenerTodosLosClientes();
        var responses = clientes.stream()
                .map(clienteMapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }
    
    /**
     * Actualiza un cliente existente.
     * 
     * @param id Identificador del cliente
     * @param request Datos actualizados del cliente
     * @return Cliente actualizado
     */
    @PutMapping("/{id}")
    public ResponseEntity<ClienteResponse> actualizarCliente(
            @PathVariable Long id, 
            @Valid @RequestBody ClienteRequest request) {
        var cliente = clienteUseCase.actualizarCliente(id, clienteMapper.toDomain(request));
        return ResponseEntity.ok(clienteMapper.toResponse(cliente));
    }
    
    /**
     * Elimina un cliente.
     * 
     * @param id Identificador del cliente
     * @return Respuesta vacía
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCliente(@PathVariable Long id) {
        clienteUseCase.eliminarCliente(id);
        return ResponseEntity.noContent().build();
    }
}
