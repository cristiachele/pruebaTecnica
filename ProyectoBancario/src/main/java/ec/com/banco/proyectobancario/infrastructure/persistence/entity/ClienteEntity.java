package ec.com.banco.proyectobancario.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entidad JPA que representa un Cliente en la base de datos.
 * Hereda de PersonaEntity.
 * 
 */
@Entity
@Table(name = "clientes", uniqueConstraints = {
    @UniqueConstraint(columnNames = "cliente_id", name = "uk_cliente_id")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@PrimaryKeyJoinColumn(name = "persona_id")
public class ClienteEntity extends PersonaEntity {
    
    @Column(name = "cliente_id", nullable = false, unique = true, length = 50)
    private String clienteId;
    
    @Column(nullable = false, length = 255)
    private String contrasena;
    
    @Column(nullable = false)
    private Boolean estado;
}
