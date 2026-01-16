package ec.com.dinersclub.proyectobancario.application.usecase;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import ec.com.dinersclub.proyectobancario.domain.model.Movimiento;
import ec.com.dinersclub.proyectobancario.domain.model.TipoMovimiento;
import ec.com.dinersclub.proyectobancario.domain.port.output.CuentaRepositoryPort;
import ec.com.dinersclub.proyectobancario.domain.port.output.MovimientoRepositoryPort;

/**
 * Caso de uso para la gestión de movimientos.
 * Contiene la lógica de negocio para las operaciones de movimientos bancarios.
 * 
 * @author Sistema Bancario
 * @version 1.0.0
 */
public class MovimientoUseCase {
    
    private final MovimientoRepositoryPort movimientoRepository;
    private final CuentaRepositoryPort cuentaRepository;
    private final BigDecimal limiteDiarioRetiro;
    
    public MovimientoUseCase(MovimientoRepositoryPort movimientoRepository, 
                            CuentaRepositoryPort cuentaRepository,
                            BigDecimal limiteDiarioRetiro) {
        this.movimientoRepository = movimientoRepository;
        this.cuentaRepository = cuentaRepository;
        this.limiteDiarioRetiro = limiteDiarioRetiro;
    }
    
    /**
     * Crea un nuevo movimiento bancario.
     * Aplica las reglas de negocio: validación de saldo, límite diario, etc.
     * 
     * @param movimiento Movimiento a crear
     * @return Movimiento creado con saldo actualizado
     * @throws IllegalArgumentException si no se cumplen las reglas de negocio
     */
    public Movimiento crearMovimiento(Movimiento movimiento) {
        // Validar que la cuenta existe
        var cuenta = cuentaRepository.findById(movimiento.getCuentaId())
                .orElseThrow(() -> new IllegalArgumentException("Cuenta no encontrada con id: " + movimiento.getCuentaId()));
        
        if (!cuenta.getEstado()) {
            throw new IllegalArgumentException("La cuenta está inactiva");
        }
        
        // Obtener el último saldo de la cuenta
        BigDecimal saldoActual = obtenerSaldoActual(cuenta.getId());
        
        // Validar tipo de movimiento y aplicar reglas de negocio
        if (TipoMovimiento.DEBITO.getDescripcion().equals(movimiento.getTipoMovimiento())) {
            // Validar saldo disponible para débitos
            if (saldoActual.compareTo(BigDecimal.ZERO) <=0) {
                throw new IllegalArgumentException("Saldo no disponible");
            }
            
            // El valor debe ser negativo para débitos
            if (movimiento.getValor().compareTo(BigDecimal.ZERO) > 0) {
                movimiento.setValor(movimiento.getValor().negate());
            }
            
            // Validar límite diario de retiro
            validarLimiteDiarioRetiro(cuenta.getId(), movimiento.getValor().abs());
            
            // Validar que el saldo sea suficiente
            if (saldoActual.add(movimiento.getValor()).compareTo(BigDecimal.ZERO) < 0) {
                throw new IllegalArgumentException("Saldo no disponible");
            }
        } else if (TipoMovimiento.CREDITO.getDescripcion().equals(movimiento.getTipoMovimiento())) {
            // El valor debe ser positivo para créditos
            if (movimiento.getValor().compareTo(BigDecimal.ZERO) < 0) {
                movimiento.setValor(movimiento.getValor().abs());
            }
        }
        
        // Calcular nuevo saldo
        BigDecimal nuevoSaldo = saldoActual.add( movimiento.getValor());
        movimiento.setSaldo(nuevoSaldo);
        movimiento.setFecha(LocalDateTime.now());
        
        // Guardar movimiento
        Movimiento movimientoGuardado = movimientoRepository.save(movimiento);
        
        // Actualizar saldo inicial de la cuenta
        cuenta.setSaldoInicial(nuevoSaldo);
        cuentaRepository.save(cuenta);
        
        return movimientoGuardado;
    }
    
    /**
     * Obtiene el saldo actual de una cuenta basado en sus movimientos.
     * 
     * @param cuentaId Identificador de la cuenta
     * @return Saldo actual
     */
    private BigDecimal obtenerSaldoActual(Long cuentaId) {
        var cuenta = cuentaRepository.findById(cuentaId)
                .orElseThrow(() -> new IllegalArgumentException("Cuenta no encontrada"));
        
        List<Movimiento> movimientos = movimientoRepository.findByCuentaId(cuentaId);
        
        if (movimientos.isEmpty()) {
            return cuenta.getSaldoInicial() != null ? cuenta.getSaldoInicial() : BigDecimal.ZERO;
        }
        
        // El último movimiento tiene el saldo actualizado
        return movimientos.stream()
                .max((m1, m2) -> m1.getFecha().compareTo(m2.getFecha()))
                .map(Movimiento::getSaldo)
                .orElse(cuenta.getSaldoInicial() != null ? cuenta.getSaldoInicial() : BigDecimal.ZERO);
    }
    
    /**
     * Valida el límite diario de retiro.
     * 
     * @param cuentaId Identificador de la cuenta
     * @param montoRetiro Monto a retirar
     * @throws IllegalArgumentException si se excede el límite diario
     */
    private void validarLimiteDiarioRetiro(Long cuentaId, BigDecimal montoRetiro) {
        LocalDate hoy = LocalDate.now();
        LocalDateTime inicioDia = hoy.atStartOfDay();
        LocalDateTime finDia = hoy.atTime(LocalTime.MAX);
        
        List<Movimiento> debitosHoy = movimientoRepository.findDebitosByCuentaIdAndFecha(cuentaId, inicioDia);
        
        BigDecimal totalRetiradoHoy = debitosHoy.stream()
                .map(m ->m.getValor())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        if (totalRetiradoHoy.add(montoRetiro).compareTo(limiteDiarioRetiro) > 0) {
            throw new IllegalArgumentException("Cupo diario Excedido");
        }
    }
    
    /**
     * Obtiene un movimiento por su identificador.
     * 
     * @param id Identificador del movimiento
     * @return Movimiento encontrado
     * @throws IllegalArgumentException si el movimiento no existe
     */
    public Movimiento obtenerMovimientoPorId(Long id) {
        return movimientoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Movimiento no encontrado con id: " + id));
    }
    
    /**
     * Obtiene todos los movimientos de una cuenta.
     * 
     * @param cuentaId Identificador de la cuenta
     * @return Lista de movimientos
     */
    public List<Movimiento> obtenerMovimientosPorCuenta(Long cuentaId) {
        return movimientoRepository.findByCuentaId(cuentaId);
    }
    
    /**
     * Obtiene movimientos de una cuenta en un rango de fechas.
     * 
     * @param cuentaId Identificador de la cuenta
     * @param fechaInicio Fecha de inicio
     * @param fechaFin Fecha de fin
     * @return Lista de movimientos
     */
    public List<Movimiento> obtenerMovimientosPorCuentaYRango(Long cuentaId, LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        return movimientoRepository.findByCuentaIdAndFechaBetween(cuentaId, fechaInicio, fechaFin);
    }
    
    /**
     * Obtiene todos los movimientos.
     * 
     * @return Lista de movimientos
     */
    public List<Movimiento> obtenerTodosLosMovimientos() {
        return movimientoRepository.findAll();
    }
    
    /**
     * Elimina un movimiento por su identificador.
     * 
     * @param id Identificador del movimiento
     * @throws IllegalArgumentException si el movimiento no existe
     */
    public void eliminarMovimiento(Long id) {
        obtenerMovimientoPorId(id); // Verifica que existe
        movimientoRepository.deleteById(id);
    }
}
