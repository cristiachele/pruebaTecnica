package ec.com.banco.proyectobancario.infrastructure.persistence.mapper;

import ec.com.banco.proyectobancario.domain.model.Cuenta;
import ec.com.banco.proyectobancario.infrastructure.persistence.entity.CuentaEntity;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper para convertir entre Cuenta (dominio) y CuentaEntity (infraestructura).
 * 
 */
@Component("cuentaPersistenceMapper")
public class CuentaMapper {
    
    /**
     * Convierte una CuentaEntity a Cuenta del dominio.
     * 
     * @param entity Entidad JPA
     * @return Modelo de dominio
     */
    public Cuenta toDomain(CuentaEntity entity) {
        if (entity == null) {
            return null;
        }
        
        Cuenta cuenta = new Cuenta();
        cuenta.setId(entity.getId());
        cuenta.setNumeroCuenta(entity.getNumeroCuenta());
        cuenta.setTipoCuenta(entity.getTipoCuenta());
        cuenta.setSaldoInicial(entity.getSaldoInicial());
        cuenta.setEstado(entity.getEstado());
        cuenta.setClienteId(entity.getClienteId());
        
        return cuenta;
    }
    
    /**
     * Convierte un Cuenta del dominio a CuentaEntity.
     * 
     * @param cuenta Modelo de dominio
     * @return Entidad JPA
     */
    public CuentaEntity toEntity(Cuenta cuenta) {
        if (cuenta == null) {
            return null;
        }
        
        CuentaEntity entity = new CuentaEntity();
        entity.setId(cuenta.getId());
        entity.setNumeroCuenta(cuenta.getNumeroCuenta());
        entity.setTipoCuenta(cuenta.getTipoCuenta());
        entity.setSaldoInicial(cuenta.getSaldoInicial());
        entity.setEstado(cuenta.getEstado());
        entity.setClienteId(cuenta.getClienteId());
        
        return entity;
    }
    
    /**
     * Convierte una lista de entidades a lista de modelos de dominio.
     * 
     * @param entities Lista de entidades
     * @return Lista de modelos de dominio
     */
    public List<Cuenta> toDomainList(List<CuentaEntity> entities) {
        if (entities == null) {
            return null;
        }
        return entities.stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }
}
