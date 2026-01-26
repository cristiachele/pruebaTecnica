package ec.com.banco.proyectobancario.adapter.input.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO para la respuesta de clientes.
 * 
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClienteResponse {
    
    private Long id;
    private String nombre;
    private String genero;
    private Integer edad;
    private String identificacion;
    private String direccion;
    private String telefono;
    private String clienteId;
    private Boolean estado;
}
