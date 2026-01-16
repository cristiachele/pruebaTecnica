package ec.com.dinersclub.proyectobancario.infrastructure.persistence.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entidad JPA que representa un Movimiento en la base de datos.
 * 
 * @author Sistema Bancario
 * @version 1.0.0
 */
@Entity
@Table(name = "movimientos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MovimientoEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private LocalDateTime fecha;
    
    @Column(name = "tipo_movimiento", nullable = false, length = 50)
    private String tipoMovimiento;
    
    @Column(nullable = false)
    private BigDecimal valor;
    
    @Column(nullable = false)
    private BigDecimal saldo;
    
    @Column(name = "cuenta_id", nullable = false)
    private Long cuentaId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cuenta_id", insertable = false, updatable = false)
    private CuentaEntity cuenta;
}
