package ec.com.dinersclub.proyectobancario.config;

import ec.com.dinersclub.proyectobancario.application.usecase.ClienteUseCase;
import ec.com.dinersclub.proyectobancario.application.usecase.CuentaUseCase;
import ec.com.dinersclub.proyectobancario.application.usecase.MovimientoUseCase;
import ec.com.dinersclub.proyectobancario.application.usecase.ReporteUseCase;
import ec.com.dinersclub.proyectobancario.domain.port.output.ClienteRepositoryPort;
import ec.com.dinersclub.proyectobancario.domain.port.output.CuentaRepositoryPort;
import ec.com.dinersclub.proyectobancario.domain.port.output.MovimientoRepositoryPort;
import ec.com.dinersclub.proyectobancario.domain.port.output.ReporteServicePort;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuración de los casos de uso.
 * 
 * @author Sistema Bancario
 * @version 1.0.0
 */
@Configuration
public class UseCaseConfig {
    
    @Bean
    public ClienteUseCase clienteUseCase(ClienteRepositoryPort clienteRepository) {
        return new ClienteUseCase(clienteRepository);
    }
    
    @Bean
    public CuentaUseCase cuentaUseCase(CuentaRepositoryPort cuentaRepository, 
                                      ClienteRepositoryPort clienteRepository) {
        return new CuentaUseCase(cuentaRepository, clienteRepository);
    }
    
    @Bean
    public MovimientoUseCase movimientoUseCase(MovimientoRepositoryPort movimientoRepository,
                                               CuentaRepositoryPort cuentaRepository,
                                               @Value("${app.daily-withdrawal-limit:1000.00}") BigDecimal limiteDiarioRetiro) {
        return new MovimientoUseCase(movimientoRepository, cuentaRepository, limiteDiarioRetiro);
    }
    
    @Bean
    public ReporteUseCase reporteUseCase(ReporteServicePort reporteService,
                                        ClienteRepositoryPort clienteRepository) {
        return new ReporteUseCase(reporteService, clienteRepository);
    }
}
