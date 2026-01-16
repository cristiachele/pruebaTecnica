package ec.com.dinersclub.proyectobancario.domain.model;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entidad de dominio que representa una Cuenta bancaria.
 * 
 * @author Sistema Bancario
 * @version 1.0.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Cuenta {
    
    /**
     * Identificador único de la cuenta (clave primaria).
     */
    private Long id;
    
    /**
     * Número de cuenta bancaria.
     */
    private String numeroCuenta;
    
    /**
     * Tipo de cuenta (Ahorro, Corriente).
     */
    private String tipoCuenta;
    
    /**
     * Saldo inicial de la cuenta.
     */
    private BigDecimal saldoInicial;
    
    /**
     * Estado de la cuenta (activa/inactiva).
     */
    private Boolean estado;
    
    /**
     * Identificador del cliente propietario de la cuenta.
     */
    private Long clienteId;
}
