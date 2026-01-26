package ec.com.banco.proyectobancario.domain.port.output;

import java.time.LocalDateTime;

import ec.com.banco.proyectobancario.domain.model.ReporteEstadoCuenta;

/**
 * Puerto de salida para la generación de reportes.
 * 
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
