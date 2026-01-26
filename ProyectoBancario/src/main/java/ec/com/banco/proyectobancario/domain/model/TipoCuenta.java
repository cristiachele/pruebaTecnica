package ec.com.banco.proyectobancario.domain.model;

/**
 * Enum que representa los tipos de cuenta bancaria.
 * 
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
