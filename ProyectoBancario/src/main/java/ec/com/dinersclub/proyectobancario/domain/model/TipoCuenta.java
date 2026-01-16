package ec.com.dinersclub.proyectobancario.domain.model;

/**
 * Enum que representa los tipos de cuenta bancaria.
 * 
 * @author Sistema Bancario
 * @version 1.0.0
 */
public enum TipoCuenta {
    
    /**
     * Cuenta de ahorros.
     */
    AHORRO("Ahorro"),
    
    /**
     * Cuenta corriente.
     */
    CORRIENTE("Corriente");
    
    private final String descripcion;
    
    TipoCuenta(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public String getDescripcion() {
        return descripcion;
    }
}
