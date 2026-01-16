package ec.com.dinersclub.proyectobancario.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entidad de dominio que representa un Cliente.
 * Hereda de Persona y agrega información específica del cliente bancario.
 * 
 * @author Sistema Bancario
 * @version 1.0.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Cliente extends Persona {
    
    /**
     * Identificador único del cliente.
     */
    private String clienteId;
    
    /**
     * Contraseña del cliente para autenticación.
     */
    private String contrasena;
    
    /**
     * Estado del cliente (activo/inactivo).
     */
    private Boolean estado;
}
