package ec.com.banco.proyectobancario.adapter.input.web.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO para la creación y actualización de cuentas.
 * 
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CuentaRequest {
    
    @NotBlank(message = "El número de cuenta es obligatorio")
    private String numeroCuenta;
    
    @NotBlank(message = "El tipo de cuenta es obligatorio")
    private String tipoCuenta;
    
    @NotNull(message = "El saldo inicial es obligatorio")
    private BigDecimal saldoInicial;
    
    @NotNull(message = "El estado es obligatorio")
    private Boolean estado;
    
    @NotNull(message = "El clienteId es obligatorio")
    private Long clienteId;
}
