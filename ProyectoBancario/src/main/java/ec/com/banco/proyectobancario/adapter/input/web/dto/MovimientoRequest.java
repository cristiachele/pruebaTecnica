package ec.com.banco.proyectobancario.adapter.input.web.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO para la creación de movimientos.
 * 
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MovimientoRequest {
    
    @NotBlank(message = "El tipo de movimiento es obligatorio")
    private String tipoMovimiento;
    
    @NotNull(message = "El valor es obligatorio")
    private BigDecimal valor;
    
    @NotNull(message = "El cuentaId es obligatorio")
    private Long cuentaId;
}
