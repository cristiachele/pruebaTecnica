package ec.com.banco.proyectobancario.config;

import ec.com.banco.proyectobancario.application.usecase.ClienteUseCase;
import ec.com.banco.proyectobancario.application.usecase.CuentaUseCase;
import ec.com.banco.proyectobancario.application.usecase.MovimientoUseCase;
import ec.com.banco.proyectobancario.application.usecase.ReporteUseCase;
import ec.com.banco.proyectobancario.domain.port.output.ClienteRepositoryPort;
import ec.com.banco.proyectobancario.domain.port.output.CuentaRepositoryPort;
import ec.com.banco.proyectobancario.domain.port.output.MovimientoRepositoryPort;
import ec.com.banco.proyectobancario.domain.port.output.ReporteServicePort;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuración de los casos de uso.
 * 
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
