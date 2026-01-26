package ec.com.banco.proyectobancario.adapter.input.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO para la respuesta de movimientos.
 * 
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MovimientoResponse {
    
    private Long id;
    private LocalDateTime fecha;
    private String tipoMovimiento;
    private BigDecimal valor;
    private BigDecimal saldo;
    private Long cuentaId;
}
