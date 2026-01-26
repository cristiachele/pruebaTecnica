package ec.com.banco.proyectobancario.infrastructure.persistence.adapter;

import ec.com.banco.proyectobancario.domain.model.Cuenta;
import ec.com.banco.proyectobancario.domain.port.output.CuentaRepositoryPort;
import ec.com.banco.proyectobancario.infrastructure.persistence.entity.CuentaEntity;
import ec.com.banco.proyectobancario.infrastructure.persistence.mapper.CuentaMapper;
import ec.com.banco.proyectobancario.infrastructure.persistence.repository.CuentaJpaRepository;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * Adaptador que implementa CuentaRepositoryPort usando JPA.
 * 
 */
@Component
public class CuentaRepositoryAdapter implements CuentaRepositoryPort {
    
    private final CuentaJpaRepository jpaRepository;
    private final CuentaMapper mapper;
    
    public CuentaRepositoryAdapter(CuentaJpaRepository jpaRepository, CuentaMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }
    
    @Override
    public Cuenta save(Cuenta cuenta) {
        CuentaEntity entity = mapper.toEntity(cuenta);
        CuentaEntity savedEntity = jpaRepository.save(entity);
        return mapper.toDomain(savedEntity);
    }
    
    @Override
    public Optional<Cuenta> findById(Long id) {
        return jpaRepository.findById(id)
                .map(mapper::toDomain);
    }
    
    @Override
    public Optional<Cuenta> findByNumeroCuenta(String numeroCuenta) {
        return jpaRepository.findByNumeroCuenta(numeroCuenta)
                .map(mapper::toDomain);
    }
    
    @Override
    public List<Cuenta> findByClienteId(Long clienteId) {
        return mapper.toDomainList(jpaRepository.findByClienteId(clienteId));
    }
    
    @Override
    public List<Cuenta> findAll() {
        return mapper.toDomainList(jpaRepository.findAll());
    }
    
    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }
    
    @Override
    public boolean existsByNumeroCuenta(String numeroCuenta) {
        return jpaRepository.existsByNumeroCuenta(numeroCuenta);
    }
}
