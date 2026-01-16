package ec.com.dinersclub.proyectobancario.application.usecase;

import ec.com.dinersclub.proyectobancario.domain.model.Cuenta;
import ec.com.dinersclub.proyectobancario.domain.port.output.ClienteRepositoryPort;
import ec.com.dinersclub.proyectobancario.domain.port.output.CuentaRepositoryPort;

import java.util.List;

/**
 * Caso de uso para la gestión de cuentas.
 * Contiene la lógica de negocio para las operaciones CRUD de cuentas.
 * 
 * @author Sistema Bancario
 * @version 1.0.0
 */
public class CuentaUseCase {
    
    private final CuentaRepositoryPort cuentaRepository;
    private final ClienteRepositoryPort clienteRepository;
    
    public CuentaUseCase(CuentaRepositoryPort cuentaRepository, ClienteRepositoryPort clienteRepository) {
        this.cuentaRepository = cuentaRepository;
        this.clienteRepository = clienteRepository;
    }
    
    /**
     * Crea una nueva cuenta.
     * 
     * @param cuenta Cuenta a crear
     * @return Cuenta creada
     * @throws IllegalArgumentException si el número de cuenta ya existe o el cliente no existe
     */
    public Cuenta crearCuenta(Cuenta cuenta) {
        if (cuentaRepository.existsByNumeroCuenta(cuenta.getNumeroCuenta())) {
            throw new IllegalArgumentException("El número de cuenta ya existe: " + cuenta.getNumeroCuenta());
        }
        
        // Verificar que el cliente existe
        clienteRepository.findById(cuenta.getClienteId())
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado con id: " + cuenta.getClienteId()));
        
        return cuentaRepository.save(cuenta);
    }
    
    /**
     * Obtiene una cuenta por su identificador.
     * 
     * @param id Identificador de la cuenta
     * @return Cuenta encontrada
     * @throws IllegalArgumentException si la cuenta no existe
     */
    public Cuenta obtenerCuentaPorId(Long id) {
        return cuentaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cuenta no encontrada con id: " + id));
    }
    
    /**
     * Obtiene una cuenta por su número de cuenta.
     * 
     * @param numeroCuenta Número de cuenta
     * @return Cuenta encontrada
     * @throws IllegalArgumentException si la cuenta no existe
     */
    public Cuenta obtenerCuentaPorNumero(String numeroCuenta) {
        return cuentaRepository.findByNumeroCuenta(numeroCuenta)
                .orElseThrow(() -> new IllegalArgumentException("Cuenta no encontrada con número: " + numeroCuenta));
    }
    
    /**
     * Obtiene todas las cuentas de un cliente.
     * 
     * @param clienteId Identificador del cliente
     * @return Lista de cuentas del cliente
     */
    public List<Cuenta> obtenerCuentasPorCliente(Long clienteId) {
        return cuentaRepository.findByClienteId(clienteId);
    }
    
    /**
     * Obtiene todas las cuentas.
     * 
     * @return Lista de cuentas
     */
    public List<Cuenta> obtenerTodasLasCuentas() {
        return cuentaRepository.findAll();
    }
    
    /**
     * Actualiza una cuenta existente.
     * 
     * @param id Identificador de la cuenta
     * @param cuentaActualizada Datos actualizados de la cuenta
     * @return Cuenta actualizada
     * @throws IllegalArgumentException si la cuenta no existe o el número de cuenta ya existe
     */
    public Cuenta actualizarCuenta(Long id, Cuenta cuentaActualizada) {
        Cuenta cuentaExistente = obtenerCuentaPorId(id);
        
        // Verificar si se está cambiando el número de cuenta y si ya existe
        if (!cuentaExistente.getNumeroCuenta().equals(cuentaActualizada.getNumeroCuenta()) 
                && cuentaRepository.existsByNumeroCuenta(cuentaActualizada.getNumeroCuenta())) {
            throw new IllegalArgumentException("El número de cuenta ya existe: " + cuentaActualizada.getNumeroCuenta());
        }
        
        cuentaActualizada.setId(id);
        return cuentaRepository.save(cuentaActualizada);
    }
    
    /**
     * Elimina una cuenta por su identificador.
     * 
     * @param id Identificador de la cuenta
     * @throws IllegalArgumentException si la cuenta no existe
     */
    public void eliminarCuenta(Long id) {
        obtenerCuentaPorId(id); // Verifica que existe
        cuentaRepository.deleteById(id);
    }
}
