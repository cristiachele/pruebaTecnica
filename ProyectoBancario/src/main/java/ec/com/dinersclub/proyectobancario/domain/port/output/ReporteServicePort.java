package ec.com.dinersclub.proyectobancario.domain.port.output;

import ec.com.dinersclub.proyectobancario.domain.model.ReporteEstadoCuenta;

import java.time.LocalDateTime;

/**
 * Puerto de salida para la generación de reportes.
 * 
 * @author Sistema Bancario
 * @version 1.0.0
 */
public interface ReporteServicePort {
    
    /**
     * Genera un reporte de estado de cuenta en formato JSON.
     * 
     * @param clienteId Identificador del cliente
     * @param fechaInicio Fecha de inicio del rango
     * @param fechaFin Fecha de fin del rango
     * @return Reporte de estado de cuenta
     */
    ReporteEstadoCuenta generarReporteEstadoCuenta(Long clienteId, LocalDateTime fechaInicio, LocalDateTime fechaFin);
    
    /**
     * Genera un reporte de estado de cuenta en formato PDF codificado en base64.
     * 
     * @param clienteId Identificador del cliente
     * @param fechaInicio Fecha de inicio del rango
     * @param fechaFin Fecha de fin del rango
     * @return Reporte en formato base64
     */
    String generarReportePdfBase64(Long clienteId, LocalDateTime fechaInicio, LocalDateTime fechaFin);
}
