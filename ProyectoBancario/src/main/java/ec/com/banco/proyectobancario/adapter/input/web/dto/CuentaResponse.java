package ec.com.banco.proyectobancario.adapter.input.web.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO para la respuesta de cuentas.
 * 
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CuentaResponse {
    
    private Long id;
    private String numeroCuenta;
    private String tipoCuenta;
    private BigDecimal saldoInicial;
    private Boolean estado;
    private Long clienteId;
}
