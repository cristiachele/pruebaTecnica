package ec.com.banco.proyectobancario.adapter.input.web.mapper;

import ec.com.banco.proyectobancario.adapter.input.web.dto.MovimientoRequest;
import ec.com.banco.proyectobancario.adapter.input.web.dto.MovimientoResponse;
import ec.com.banco.proyectobancario.domain.model.Movimiento;

import org.springframework.stereotype.Component;

/**
 * Mapper para convertir entre DTOs y modelos de dominio de Movimiento.
 * 
 */
@Component("movimientoWebMapper")
public class MovimientoMapper {
    
    /**
     * Convierte un MovimientoRequest a Movimiento del dominio.
     * 
     * @param request DTO de request
     * @return Modelo de dominio
     */
    public Movimiento toDomain(MovimientoRequest request) {
        if (request == null) {
            return null;
        }
        
        Movimiento movimiento = new Movimiento();
        movimiento.setTipoMovimiento(request.getTipoMovimiento());
        movimiento.setValor(request.getValor());
        movimiento.setCuentaId(request.getCuentaId());
        
        return movimiento;
    }
    
    /**
     * Convierte un Movimiento del dominio a MovimientoResponse.
     * 
     * @param movimiento Modelo de dominio
     * @return DTO de response
     */
    public MovimientoResponse toResponse(Movimiento movimiento) {
        if (movimiento == null) {
            return null;
        }
        
        MovimientoResponse response = new MovimientoResponse();
        response.setId(movimiento.getId());
        response.setFecha(movimiento.getFecha());
        response.setTipoMovimiento(movimiento.getTipoMovimiento());
        response.setValor(movimiento.getValor());
        response.setSaldo(movimiento.getSaldo());
        response.setCuentaId(movimiento.getCuentaId());
        
        return response;
    }
}
