package ec.com.banco.proyectobancario.infrastructure.persistence.mapper;

import ec.com.banco.proyectobancario.domain.model.Movimiento;
import ec.com.banco.proyectobancario.infrastructure.persistence.entity.MovimientoEntity;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper para convertir entre Movimiento (dominio) y MovimientoEntity (infraestructura).
 * 
 */
@Component("movimientoPersistenceMapper")
public class MovimientoMapper {
    
    /**
     * Convierte una MovimientoEntity a Movimiento del dominio.
     * 
     * @param entity Entidad JPA
     * @return Modelo de dominio
     */
    public Movimiento toDomain(MovimientoEntity entity) {
        if (entity == null) {
            return null;
        }
        
        Movimiento movimiento = new Movimiento();
        movimiento.setId(entity.getId());
        movimiento.setFecha(entity.getFecha());
        movimiento.setTipoMovimiento(entity.getTipoMovimiento());
        movimiento.setValor(entity.getValor());
        movimiento.setSaldo(entity.getSaldo());
        movimiento.setCuentaId(entity.getCuentaId());
        
        return movimiento;
    }
    
    /**
     * Convierte un Movimiento del dominio a MovimientoEntity.
     * 
     * @param movimiento Modelo de dominio
     * @return Entidad JPA
     */
    public MovimientoEntity toEntity(Movimiento movimiento) {
        if (movimiento == null) {
            return null;
        }
        
        MovimientoEntity entity = new MovimientoEntity();
        entity.setId(movimiento.getId());
        entity.setFecha(movimiento.getFecha());
        entity.setTipoMovimiento(movimiento.getTipoMovimiento());
        entity.setValor(movimiento.getValor());
        entity.setSaldo(movimiento.getSaldo());
        entity.setCuentaId(movimiento.getCuentaId());
        
        return entity;
    }
    
    /**
     * Convierte una lista de entidades a lista de modelos de dominio.
     * 
     * @param entities Lista de entidades
     * @return Lista de modelos de dominio
     */
    public List<Movimiento> toDomainList(List<MovimientoEntity> entities) {
        if (entities == null) {
            return null;
        }
        return entities.stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }
}
