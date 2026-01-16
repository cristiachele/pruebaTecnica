package ec.com.dinersclub.proyectobancario.infrastructure.persistence.adapter;

import ec.com.dinersclub.proyectobancario.domain.model.Movimiento;
import ec.com.dinersclub.proyectobancario.domain.port.output.MovimientoRepositoryPort;
import ec.com.dinersclub.proyectobancario.infrastructure.persistence.entity.MovimientoEntity;
import ec.com.dinersclub.proyectobancario.infrastructure.persistence.mapper.MovimientoMapper;
import ec.com.dinersclub.proyectobancario.infrastructure.persistence.repository.MovimientoJpaRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

/**
 * Adaptador que implementa MovimientoRepositoryPort usando JPA.
 * 
 * @author Sistema Bancario
 * @version 1.0.0
 */
@Component
public class MovimientoRepositoryAdapter implements MovimientoRepositoryPort {
    
    private final MovimientoJpaRepository jpaRepository;
    private final MovimientoMapper mapper;
    
    public MovimientoRepositoryAdapter(MovimientoJpaRepository jpaRepository, MovimientoMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }
    
    @Override
    public Movimiento save(Movimiento movimiento) {
        MovimientoEntity entity = mapper.toEntity(movimiento);
        MovimientoEntity savedEntity = jpaRepository.save(entity);
        return mapper.toDomain(savedEntity);
    }
    
    @Override
    public Optional<Movimiento> findById(Long id) {
        return jpaRepository.findById(id)
                .map(mapper::toDomain);
    }
    
    @Override
    public List<Movimiento> findByCuentaId(Long cuentaId) {
        return mapper.toDomainList(jpaRepository.findByCuentaId(cuentaId));
    }
    
    @Override
    public List<Movimiento> findByCuentaIdAndFechaBetween(Long cuentaId, LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        return mapper.toDomainList(jpaRepository.findByCuentaIdAndFechaBetween(cuentaId, fechaInicio, fechaFin));
    }
    
    @Override
    public List<Movimiento> findDebitosByCuentaIdAndFecha(Long cuentaId, LocalDateTime fecha) {
        LocalDate fechaLocal = fecha.toLocalDate();
        LocalDateTime inicioDia = fechaLocal.atStartOfDay();
        LocalDateTime finDia = fechaLocal.atTime(LocalTime.MAX);
        
        return mapper.toDomainList(jpaRepository.findDebitosByCuentaIdAndFechaBetween(cuentaId, inicioDia, finDia));
    }
    
    @Override
    public List<Movimiento> findAll() {
        return mapper.toDomainList(jpaRepository.findAll());
    }
    
    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }
}
