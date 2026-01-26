package ec.com.banco.proyectobancario.adapter.input.web.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ec.com.banco.proyectobancario.application.usecase.ReporteUseCase;
import ec.com.banco.proyectobancario.domain.model.ReporteEstadoCuenta;

/**
 * Controlador REST para la generación de reportes.
 * 
 */
@RestController
@RequestMapping("/reportes")
@CrossOrigin(origins = "*")
public class ReporteController {
    
    private final ReporteUseCase reporteUseCase;
    
    public ReporteController(ReporteUseCase reporteUseCase) {
        this.reporteUseCase = reporteUseCase;
    }
    
    /**
     * Genera un reporte de estado de cuenta en formato JSON.
     * 
     * @param clienteId Identificador del cliente
     * @param fechaInicio Fecha de inicio del rango
     * @param fechaFin Fecha de fin del rango
     * @return Reporte en formato JSON
     */
    @GetMapping
    public ResponseEntity<ReporteEstadoCuenta> generarReporte(
            @RequestParam Long clienteId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaFin) {
        var reporte = reporteUseCase.generarReporteEstadoCuenta(clienteId, fechaInicio, fechaFin);
        return ResponseEntity.ok(reporte);
    }
    
    /**
     * Genera un reporte de estado de cuenta en formato PDF codificado en base64.
     * 
     * @param clienteId Identificador del cliente
     * @param fechaInicio Fecha de inicio del rango
     * @param fechaFin Fecha de fin del rango
     * @return Reporte en formato base64
     */
    @GetMapping("/pdf")
    public ResponseEntity<Map<String, String>> generarReportePdf(
            @RequestParam Long clienteId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaFin) {
        String pdfBase64 = reporteUseCase.generarReportePdfBase64(clienteId, fechaInicio, fechaFin);
        
        Map<String, String> response = new HashMap<>();
        response.put("pdf", pdfBase64);
        
        return ResponseEntity.ok(response);
    }
}
