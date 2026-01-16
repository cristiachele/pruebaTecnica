package ec.com.dinersclub.proyectobancario.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

/**
 * Modelo de dominio que representa un reporte de estado de cuenta.
 * 
 * @author Sistema Bancario
 * @version 1.0.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReporteEstadoCuenta {
    
    /**
     * Identificador del cliente.
     */
    private Long clienteId;
    
    /**
     * Nombre del cliente.
     */
    private String clienteNombre;
    
    /**
     * Fecha de inicio del reporte.
     */
    private String fechaInicio;
    
    /**
     * Fecha de fin del reporte.
     */
    private String fechaFin;
    
    /**
     * Lista de cuentas con sus movimientos.
     */
    private List<CuentaReporte> cuentas;
    
    /**
     * Total de créditos en el período.
     */
    private BigDecimal totalCreditos;
    
    /**
     * Total de débitos en el período.
     */
    private BigDecimal totalDebitos;
    
    /**
     * Clase interna que representa una cuenta en el reporte.
     */
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CuentaReporte {
        private String numeroCuenta;
        private String tipoCuenta;
        private BigDecimal saldoInicial;
        private BigDecimal saldoActual;
        private Boolean estado;
        private List<MovimientoReporte> movimientos;
    }
    
    /**
     * Clase interna que representa un movimiento en el reporte.
     */
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MovimientoReporte {
        private String fecha;
        private String tipoMovimiento;
        private BigDecimal valor;
        private BigDecimal saldo;
    }
}
