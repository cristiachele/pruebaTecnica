package ec.com.banco.proyectobancario.infrastructure.persistence.adapter;

import ec.com.banco.proyectobancario.domain.model.Cliente;
import ec.com.banco.proyectobancario.domain.port.output.ClienteRepositoryPort;
import ec.com.banco.proyectobancario.infrastructure.persistence.entity.ClienteEntity;
import ec.com.banco.proyectobancario.infrastructure.persistence.mapper.ClienteMapper;
import ec.com.banco.proyectobancario.infrastructure.persistence.repository.ClienteJpaRepository;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Adaptador que implementa ClienteRepositoryPort usando JPA.
 * 
 */
@Component
public class ClienteRepositoryAdapter implements ClienteRepositoryPort {
    
    private final ClienteJpaRepository jpaRepository;
    private final ClienteMapper mapper;
    
    public ClienteRepositoryAdapter(ClienteJpaRepository jpaRepository, ClienteMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }
    
    @Override
    public Cliente save(Cliente cliente) {
        ClienteEntity entity = mapper.toEntity(cliente);
        ClienteEntity savedEntity = jpaRepository.save(entity);
        return mapper.toDomain(savedEntity);
    }
    
    @Override
    public Optional<Cliente> findById(Long id) {
        return jpaRepository.findById(id)
                .map(mapper::toDomain);
    }
    
    @Override
    public Optional<Cliente> findByClienteId(String clienteId) {
        return jpaRepository.findByClienteId(clienteId)
                .map(mapper::toDomain);
    }
    
    @Override
    public List<Cliente> findAll() {
        return jpaRepository.findAll().stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }
    
    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }
    
    @Override
    public boolean existsByClienteId(String clienteId) {
        return jpaRepository.existsByClienteId(clienteId);
    }
}
