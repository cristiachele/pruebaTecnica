package ec.com.banco.proyectobancario.infrastructure.persistence.entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entidad JPA que representa una Cuenta en la base de datos.
 * 
 */
@Entity
@Table(name = "cuentas", uniqueConstraints = {
    @UniqueConstraint(columnNames = "numero_cuenta", name = "uk_numero_cuenta")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CuentaEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "numero_cuenta", nullable = false, unique = true, length = 50)
    private String numeroCuenta;
    
    @Column(name = "tipo_cuenta", nullable = false, length = 50)
    private String tipoCuenta;
    
    @Column(name = "saldo_inicial", nullable = false)
    private BigDecimal saldoInicial;
    
    @Column(nullable = false)
    private Boolean estado;
    
    @Column(name = "cliente_id", nullable = false)
    private Long clienteId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", insertable = false, updatable = false)
    private ClienteEntity cliente;
}
