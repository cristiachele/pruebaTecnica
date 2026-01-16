package ec.com.dinersclub.proyectobancario.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entidad de dominio que representa un Movimiento bancario.
 * Los créditos son positivos y los débitos son negativos.
 * 
 * @author Sistema Bancario
 * @version 1.0.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Movimiento {
    
    /**
     * Identificador único del movimiento (clave primaria).
     */
    private Long id;
    
    /**
     * Fecha y hora del movimiento.
     */
    private LocalDateTime fecha;
    
    /**
     * Tipo de movimiento (Crédito/Débito).
     */
    private String tipoMovimiento;
    
    /**
     * Valor del movimiento. Positivo para créditos, negativo para débitos.
     */
    private BigDecimal valor;
    
    /**
     * Saldo disponible después del movimiento.
     */
    private BigDecimal saldo;
    
    /**
     * Identificador de la cuenta asociada al movimiento.
     */
    private Long cuentaId;
}
