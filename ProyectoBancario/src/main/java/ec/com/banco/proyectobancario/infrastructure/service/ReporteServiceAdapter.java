package ec.com.banco.proyectobancario.infrastructure.service;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.UnitValue;

import ec.com.banco.proyectobancario.domain.model.Cliente;
import ec.com.banco.proyectobancario.domain.model.Cuenta;
import ec.com.banco.proyectobancario.domain.model.Movimiento;
import ec.com.banco.proyectobancario.domain.model.ReporteEstadoCuenta;
import ec.com.banco.proyectobancario.domain.port.output.ClienteRepositoryPort;
import ec.com.banco.proyectobancario.domain.port.output.CuentaRepositoryPort;
import ec.com.banco.proyectobancario.domain.port.output.MovimientoRepositoryPort;
import ec.com.banco.proyectobancario.domain.port.output.ReporteServicePort;

import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Adaptador que implementa ReporteServicePort para generar reportes.
 * 
 */
@Service
public class ReporteServiceAdapter implements ReporteServicePort {
    
    private final ClienteRepositoryPort clienteRepository;
    private final CuentaRepositoryPort cuentaRepository;
    private final MovimientoRepositoryPort movimientoRepository;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    
    public ReporteServiceAdapter(ClienteRepositoryPort clienteRepository,
                                 CuentaRepositoryPort cuentaRepository,
                                 MovimientoRepositoryPort movimientoRepository) {
        this.clienteRepository = clienteRepository;
        this.cuentaRepository = cuentaRepository;
        this.movimientoRepository = movimientoRepository;
    }
    
    @Override
    public ReporteEstadoCuenta generarReporteEstadoCuenta(Long clienteId, LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado"));
        
        List<Cuenta> cuentas = cuentaRepository.findByClienteId(clienteId);
        
        ReporteEstadoCuenta reporte = new ReporteEstadoCuenta();
        reporte.setClienteId(clienteId);
        reporte.setClienteNombre(cliente.getNombre());
        reporte.setFechaInicio(fechaInicio.format(DATE_FORMATTER));
        reporte.setFechaFin(fechaFin.format(DATE_FORMATTER));
        
        BigDecimal totalCreditos = BigDecimal.ZERO;
        BigDecimal totalDebitos = BigDecimal.ZERO;
        
        List<ReporteEstadoCuenta.CuentaReporte> cuentasReporte = cuentas.stream()
                .map(cuenta -> {
                    List<Movimiento> movimientos = movimientoRepository.findByCuentaIdAndFechaBetween(
                            cuenta.getId(), fechaInicio, fechaFin);
                    
                    List<ReporteEstadoCuenta.MovimientoReporte> movimientosReporte = movimientos.stream()
                            .map(mov -> {
                                ReporteEstadoCuenta.MovimientoReporte movReporte = new ReporteEstadoCuenta.MovimientoReporte();
                                movReporte.setFecha(mov.getFecha().format(DATE_FORMATTER));
                                movReporte.setTipoMovimiento(mov.getTipoMovimiento());
                                movReporte.setValor(mov.getValor());
                                movReporte.setSaldo(mov.getSaldo());
                                
                                if ("Crédito".equals(mov.getTipoMovimiento())) {
                                    totalCreditos.add(mov.getValor());
                                } else {
                                    totalDebitos.add(mov.getValor());
                                }
                                
                                return movReporte;
                            })
                            .collect(Collectors.toList());
                    
                    ReporteEstadoCuenta.CuentaReporte cuentaReporte = new ReporteEstadoCuenta.CuentaReporte();
                    cuentaReporte.setNumeroCuenta(cuenta.getNumeroCuenta());
                    cuentaReporte.setTipoCuenta(cuenta.getTipoCuenta());
                    cuentaReporte.setSaldoInicial(cuenta.getSaldoInicial());
                    cuentaReporte.setSaldoActual(obtenerSaldoActual(cuenta.getId()));
                    cuentaReporte.setEstado(cuenta.getEstado());
                    cuentaReporte.setMovimientos(movimientosReporte);
                    
                    return cuentaReporte;
                })
                .collect(Collectors.toList());
        
        reporte.setCuentas(cuentasReporte);
        reporte.setTotalCreditos(totalCreditos);
        reporte.setTotalDebitos(totalDebitos);
        
        return reporte;
    }
    
    @Override
    public String generarReportePdfBase64(Long clienteId, LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        ReporteEstadoCuenta reporte = generarReporteEstadoCuenta(clienteId, fechaInicio, fechaFin);
        
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            PdfWriter writer = new PdfWriter(baos);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);
            
            // Título
            document.add(new Paragraph("REPORTE DE ESTADO DE CUENTA")
                    .setBold()
                    .setFontSize(18));
            
            document.add(new Paragraph("Cliente: " + reporte.getClienteNombre()));
            document.add(new Paragraph("Período: " + reporte.getFechaInicio() + " - " + reporte.getFechaFin()));
            document.add(new Paragraph(" "));
            
            // Tabla de cuentas y movimientos
            for (ReporteEstadoCuenta.CuentaReporte cuenta : reporte.getCuentas()) {
                document.add(new Paragraph("Cuenta: " + cuenta.getNumeroCuenta() + " - " + cuenta.getTipoCuenta())
                        .setBold());
                document.add(new Paragraph("Saldo Inicial: $" + cuenta.getSaldoInicial()));
                document.add(new Paragraph("Saldo Actual: $" + cuenta.getSaldoActual()));
                document.add(new Paragraph(" "));
                
                if (cuenta.getMovimientos() != null && !cuenta.getMovimientos().isEmpty()) {
                    Table table = new Table(UnitValue.createPercentArray(new float[]{2, 2, 2, 2}))
                            .useAllAvailableWidth();
                    
                    table.addHeaderCell("Fecha");
                    table.addHeaderCell("Tipo");
                    table.addHeaderCell("Valor");
                    table.addHeaderCell("Saldo");
                    
                    for (ReporteEstadoCuenta.MovimientoReporte mov : cuenta.getMovimientos()) {
                        table.addCell(mov.getFecha());
                        table.addCell(mov.getTipoMovimiento());
                        table.addCell("$" + mov.getValor());
                        table.addCell("$" + mov.getSaldo());
                    }
                    
                    document.add(table);
                    document.add(new Paragraph(" "));
                }
            }
            
            // Totales
            document.add(new Paragraph("Total Créditos: $" + reporte.getTotalCreditos()).setBold());
            document.add(new Paragraph("Total Débitos: $" + reporte.getTotalDebitos()).setBold());
            
            document.close();
            
            return Base64.getEncoder().encodeToString(baos.toByteArray());
        } catch (Exception e) {
            throw new RuntimeException("Error al generar PDF: " + e.getMessage(), e);
        }
    }
    
    private BigDecimal obtenerSaldoActual(Long cuentaId) {
        List<Movimiento> movimientos = movimientoRepository.findByCuentaId(cuentaId);
        if (movimientos.isEmpty()) {
            return cuentaRepository.findById(cuentaId)
                    .map(Cuenta::getSaldoInicial)
                    .orElse(BigDecimal.ZERO);
        }
        return movimientos.stream()
                .max((m1, m2) -> m1.getFecha().compareTo(m2.getFecha()))
                .map(Movimiento::getSaldo)
                .orElse(BigDecimal.ZERO);
    }
}
