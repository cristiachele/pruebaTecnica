package ec.com.dinersclub.proyectobancario.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entidad JPA que representa una Persona en la base de datos.
 * 
 * @author Sistema Bancario
 * @version 1.0.0
 */
@Entity
@Table(name = "personas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
public class PersonaEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 100)
    private String nombre;
    
    @Column(length = 20)
    private String genero;
    
    @Column
    private Integer edad;
    
    @Column(length = 20, unique = true)
    private String identificacion;
    
    @Column(length = 200)
    private String direccion;
    
    @Column(length = 20)
    private String telefono;
}
