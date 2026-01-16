package ec.com.dinersclub.proyectobancario.adapter.input.web.controller;

import ec.com.dinersclub.proyectobancario.adapter.input.web.dto.CuentaRequest;
import ec.com.dinersclub.proyectobancario.adapter.input.web.dto.CuentaResponse;
import ec.com.dinersclub.proyectobancario.adapter.input.web.mapper.CuentaMapper;
import ec.com.dinersclub.proyectobancario.application.usecase.CuentaUseCase;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Controlador REST para la gestión de cuentas.
 * 
 * @author Sistema Bancario
 * @version 1.0.0
 */
@RestController
@RequestMapping("/cuentas")
@CrossOrigin(origins = "*")
public class CuentaController {
    
    private final CuentaUseCase cuentaUseCase;
    private final CuentaMapper cuentaMapper;
    
    public CuentaController(CuentaUseCase cuentaUseCase, CuentaMapper cuentaMapper) {
        this.cuentaUseCase = cuentaUseCase;
        this.cuentaMapper = cuentaMapper;
    }
    
    /**
     * Crea una nueva cuenta.
     * 
     * @param request Datos de la cuenta
     * @return Cuenta creada
     */
    @PostMapping
    public ResponseEntity<CuentaResponse> crearCuenta(@Valid @RequestBody CuentaRequest request) {
        var cuenta = cuentaUseCase.crearCuenta(cuentaMapper.toDomain(request));
        return new ResponseEntity<>(cuentaMapper.toResponse(cuenta), HttpStatus.CREATED);
    }
    
    /**
     * Obtiene una cuenta por su identificador.
     * 
     * @param id Identificador de la cuenta
     * @return Cuenta encontrada
     */
    @GetMapping("/{id}")
    public ResponseEntity<CuentaResponse> obtenerCuenta(@PathVariable Long id) {
        var cuenta = cuentaUseCase.obtenerCuentaPorId(id);
        return ResponseEntity.ok(cuentaMapper.toResponse(cuenta));
    }
    
    /**
     * Obtiene todas las cuentas.
     * 
     * @return Lista de cuentas
     */
    @GetMapping
    public ResponseEntity<List<CuentaResponse>> obtenerTodasLasCuentas() {
        var cuentas = cuentaUseCase.obtenerTodasLasCuentas();
        var responses = cuentas.stream()
                .map(cuentaMapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }
    
    /**
     * Obtiene todas las cuentas de un cliente.
     * 
     * @param clienteId Identificador del cliente
     * @return Lista de cuentas del cliente
     */
    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<CuentaResponse>> obtenerCuentasPorCliente(@PathVariable Long clienteId) {
        var cuentas = cuentaUseCase.obtenerCuentasPorCliente(clienteId);
        var responses = cuentas.stream()
                .map(cuentaMapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }
    
    /**
     * Actualiza una cuenta existente.
     * 
     * @param id Identificador de la cuenta
     * @param request Datos actualizados de la cuenta
     * @return Cuenta actualizada
     */
    @PutMapping("/{id}")
    public ResponseEntity<CuentaResponse> actualizarCuenta(
            @PathVariable Long id, 
            @Valid @RequestBody CuentaRequest request) {
        var cuenta = cuentaUseCase.actualizarCuenta(id, cuentaMapper.toDomain(request));
        return ResponseEntity.ok(cuentaMapper.toResponse(cuenta));
    }
    
    /**
     * Elimina una cuenta.
     * 
     * @param id Identificador de la cuenta
     * @return Respuesta vacía
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCuenta(@PathVariable Long id) {
        cuentaUseCase.eliminarCuenta(id);
        return ResponseEntity.noContent().build();
    }
}
