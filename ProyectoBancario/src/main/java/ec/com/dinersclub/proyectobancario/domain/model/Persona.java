package ec.com.dinersclub.proyectobancario.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entidad de dominio que representa una Persona.
 * Clase base para otras entidades del sistema bancario.
 * 
 * @author Sistema Bancario
 * @version 1.0.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Persona {
    
    /**
     * Identificador único de la persona (clave primaria).
     */
    private Long id;
    
    /**
     * Nombre completo de la persona.
     */
    private String nombre;
    
    /**
     * Género de la persona.
     */
    private String genero;
    
    /**
     * Edad de la persona.
     */
    private Integer edad;
    
    /**
     * Número de identificación de la persona.
     */
    private String identificacion;
    
    /**
     * Dirección de residencia de la persona.
     */
    private String direccion;
    
    /**
     * Número de teléfono de contacto.
     */
    private String telefono;
}
