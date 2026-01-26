package ec.com.banco.proyectobancario.adapter.input.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO para la creación y actualización de clientes.
 * 
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClienteRequest {
    
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;
    
    private String genero;
    
    private Integer edad;
    
    @NotBlank(message = "La identificación es obligatoria")
    private String identificacion;
    
    private String direccion;
    
    private String telefono;
    
    @NotBlank(message = "El clienteId es obligatorio")
    private String clienteId;
    
    @NotBlank(message = "La contraseña es obligatoria")
    private String contrasena;
    
    @NotNull(message = "El estado es obligatorio")
    private Boolean estado;
}
