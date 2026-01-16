package ec.com.dinersclub.proyectobancario.adapter.input.web.controller;

import ec.com.dinersclub.proyectobancario.adapter.input.web.dto.MovimientoRequest;
import ec.com.dinersclub.proyectobancario.adapter.input.web.dto.MovimientoResponse;
import ec.com.dinersclub.proyectobancario.adapter.input.web.mapper.MovimientoMapper;
import ec.com.dinersclub.proyectobancario.application.usecase.MovimientoUseCase;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Controlador REST para la gestión de movimientos.
 * 
 * @author Sistema Bancario
 * @version 1.0.0
 */
@RestController
@RequestMapping("/movimientos")
@CrossOrigin(origins = "*")
public class MovimientoController {
    
    private final MovimientoUseCase movimientoUseCase;
    private final MovimientoMapper movimientoMapper;
    
    public MovimientoController(MovimientoUseCase movimientoUseCase, MovimientoMapper movimientoMapper) {
        this.movimientoUseCase = movimientoUseCase;
        this.movimientoMapper = movimientoMapper;
    }
    
    /**
     * Crea un nuevo movimiento.
     * 
     * @param request Datos del movimiento
     * @return Movimiento creado
     */
    @PostMapping
    public ResponseEntity<MovimientoResponse> crearMovimiento(@Valid @RequestBody MovimientoRequest request) {
        var movimiento = movimientoUseCase.crearMovimiento(movimientoMapper.toDomain(request));
        return new ResponseEntity<>(movimientoMapper.toResponse(movimiento), HttpStatus.CREATED);
    }
    
    /**
     * Obtiene un movimiento por su identificador.
     * 
     * @param id Identificador del movimiento
     * @return Movimiento encontrado
     */
    @GetMapping("/{id}")
    public ResponseEntity<MovimientoResponse> obtenerMovimiento(@PathVariable Long id) {
        var movimiento = movimientoUseCase.obtenerMovimientoPorId(id);
        return ResponseEntity.ok(movimientoMapper.toResponse(movimiento));
    }
    
    /**
     * Obtiene todos los movimientos.
     * 
     * @return Lista de movimientos
     */
    @GetMapping
    public ResponseEntity<List<MovimientoResponse>> obtenerTodosLosMovimientos() {
        var movimientos = movimientoUseCase.obtenerTodosLosMovimientos();
        var responses = movimientos.stream()
                .map(movimientoMapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }
    
    /**
     * Obtiene todos los movimientos de una cuenta.
     * 
     * @param cuentaId Identificador de la cuenta
     * @return Lista de movimientos
     */
    @GetMapping("/cuenta/{cuentaId}")
    public ResponseEntity<List<MovimientoResponse>> obtenerMovimientosPorCuenta(@PathVariable Long cuentaId) {
        var movimientos = movimientoUseCase.obtenerMovimientosPorCuenta(cuentaId);
        var responses = movimientos.stream()
                .map(movimientoMapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }
    
    /**
     * Obtiene movimientos de una cuenta en un rango de fechas.
     * 
     * @param cuentaId Identificador de la cuenta
     * @param fechaInicio Fecha de inicio
     * @param fechaFin Fecha de fin
     * @return Lista de movimientos
     */
    @GetMapping("/cuenta/{cuentaId}/rango")
    public ResponseEntity<List<MovimientoResponse>> obtenerMovimientosPorRango(
            @PathVariable Long cuentaId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaFin) {
        var movimientos = movimientoUseCase.obtenerMovimientosPorCuentaYRango(cuentaId, fechaInicio, fechaFin);
        var responses = movimientos.stream()
                .map(movimientoMapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }
    
    /**
     * Elimina un movimiento.
     * 
     * @param id Identificador del movimiento
     * @return Respuesta vacía
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarMovimiento(@PathVariable Long id) {
        movimientoUseCase.eliminarMovimiento(id);
        return ResponseEntity.noContent().build();
    }
}
