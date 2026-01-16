package ec.com.dinersclub.proyectobancario.domain.model;

/**
 * Enum que representa los tipos de movimiento bancario.
 * 
 * @author Sistema Bancario
 * @version 1.0.0
 */
public enum TipoMovimiento {
    
    /**
     * Movimiento de crédito (depósito) - valores positivos.
     */
    CREDITO("Crédito"),
    
    /**
     * Movimiento de débito (retiro) - valores negativos.
     */
    DEBITO("Débito");
    
    private final String descripcion;
    
    TipoMovimiento(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public String getDescripcion() {
        return descripcion;
    }
}
