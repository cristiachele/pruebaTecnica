package ec.com.dinersclub.proyectobancario.adapter.input.web.mapper;

import ec.com.dinersclub.proyectobancario.adapter.input.web.dto.CuentaRequest;
import ec.com.dinersclub.proyectobancario.adapter.input.web.dto.CuentaResponse;
import ec.com.dinersclub.proyectobancario.domain.model.Cuenta;
import org.springframework.stereotype.Component;

/**
 * Mapper para convertir entre DTOs y modelos de dominio de Cuenta.
 * 
 * @author Sistema Bancario
 * @version 1.0.0
 */
@Component("cuentaWebMapper")
public class CuentaMapper {
    
    /**
     * Convierte un CuentaRequest a Cuenta del dominio.
     * 
     * @param request DTO de request
     * @return Modelo de dominio
     */
    public Cuenta toDomain(CuentaRequest request) {
        if (request == null) {
            return null;
        }
        
        Cuenta cuenta = new Cuenta();
        cuenta.setNumeroCuenta(request.getNumeroCuenta());
        cuenta.setTipoCuenta(request.getTipoCuenta());
        cuenta.setSaldoInicial(request.getSaldoInicial());
        cuenta.setEstado(request.getEstado());
        cuenta.setClienteId(request.getClienteId());
        
        return cuenta;
    }
    
    /**
     * Convierte un Cuenta del dominio a CuentaResponse.
     * 
     * @param cuenta Modelo de dominio
     * @return DTO de response
     */
    public CuentaResponse toResponse(Cuenta cuenta) {
        if (cuenta == null) {
            return null;
        }
        
        CuentaResponse response = new CuentaResponse();
        response.setId(cuenta.getId());
        response.setNumeroCuenta(cuenta.getNumeroCuenta());
        response.setTipoCuenta(cuenta.getTipoCuenta());
        response.setSaldoInicial(cuenta.getSaldoInicial());
        response.setEstado(cuenta.getEstado());
        response.setClienteId(cuenta.getClienteId());
        
        return response;
    }
}
