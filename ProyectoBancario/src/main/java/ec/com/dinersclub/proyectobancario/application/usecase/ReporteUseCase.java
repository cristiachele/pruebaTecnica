package ec.com.dinersclub.proyectobancario.application.usecase;

import ec.com.dinersclub.proyectobancario.domain.model.ReporteEstadoCuenta;
import ec.com.dinersclub.proyectobancario.domain.port.output.ClienteRepositoryPort;
import ec.com.dinersclub.proyectobancario.domain.port.output.ReporteServicePort;

import java.time.LocalDateTime;

/**
 * Caso de uso para la generación de reportes.
 * 
 * @author Sistema Bancario
 * @version 1.0.0
 */
public class ReporteUseCase {
    
    private final ReporteServicePort reporteService;
    private final ClienteRepositoryPort clienteRepository;
    
    public ReporteUseCase(ReporteServicePort reporteService, ClienteRepositoryPort clienteRepository) {
        this.reporteService = reporteService;
        this.clienteRepository = clienteRepository;
    }
    
    /**
     * Genera un reporte de estado de cuenta en formato JSON.
     * 
     * @param clienteId Identificador del cliente
     * @param fechaInicio Fecha de inicio del rango
     * @param fechaFin Fecha de fin del rango
     * @return Reporte de estado de cuenta
     * @throws IllegalArgumentException si el cliente no existe
     */
    public ReporteEstadoCuenta generarReporteEstadoCuenta(Long clienteId, LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        // Validar que el cliente existe
        clienteRepository.findById(clienteId)
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado con id: " + clienteId));
        
        return reporteService.generarReporteEstadoCuenta(clienteId, fechaInicio, fechaFin);
    }
    
    /**
     * Genera un reporte de estado de cuenta en formato PDF codificado en base64.
     * 
     * @param clienteId Identificador del cliente
     * @param fechaInicio Fecha de inicio del rango
     * @param fechaFin Fecha de fin del rango
     * @return Reporte en formato base64
     * @throws IllegalArgumentException si el cliente no existe
     */
    public String generarReportePdfBase64(Long clienteId, LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        // Validar que el cliente existe
        clienteRepository.findById(clienteId)
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado con id: " + clienteId));
        
        return reporteService.generarReportePdfBase64(clienteId, fechaInicio, fechaFin);
    }
}
