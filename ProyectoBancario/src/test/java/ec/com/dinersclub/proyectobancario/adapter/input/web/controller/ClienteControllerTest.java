package ec.com.dinersclub.proyectobancario.adapter.input.web.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import ec.com.dinersclub.proyectobancario.adapter.input.web.dto.ClienteRequest;
import ec.com.dinersclub.proyectobancario.adapter.input.web.dto.ClienteResponse;
import ec.com.dinersclub.proyectobancario.adapter.input.web.mapper.ClienteMapper;
import ec.com.dinersclub.proyectobancario.application.usecase.ClienteUseCase;
import ec.com.dinersclub.proyectobancario.domain.model.Cliente;

/**
 * Pruebas unitarias para ClienteController.
 * 
 * @author Sistema Bancario
 * @version 1.0.0
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("Pruebas del controlador de clientes")
class ClienteControllerTest {
    
    @Mock
    private ClienteUseCase clienteUseCase;
    
    @Mock
    private ClienteMapper clienteMapper;
    
    @InjectMocks
    private ClienteController clienteController;
    
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private ClienteRequest clienteRequest;
    private Cliente cliente;
    private ClienteResponse clienteResponse;
    
    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(clienteController).build();
        objectMapper = new ObjectMapper();
        
        clienteRequest = new ClienteRequest();
        clienteRequest.setNombre("Jose Lema");
        clienteRequest.setIdentificacion("1234567890");
        clienteRequest.setClienteId("CLI001");
        clienteRequest.setContrasena("1234");
        clienteRequest.setEstado(true);
        clienteRequest.setGenero("Masculino");
        clienteRequest.setEdad(30);
        clienteRequest.setDireccion("Otavalo sn y principal");
        clienteRequest.setTelefono("098254785");
        
        cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNombre("Jose Lema");
        cliente.setClienteId("CLI001");
        
        clienteResponse = new ClienteResponse();
        clienteResponse.setId(1L);
        clienteResponse.setNombre("Jose Lema");
        clienteResponse.setClienteId("CLI001");
        clienteResponse.setIdentificacion("1234567890");
        clienteResponse.setEstado(true);
    }
    
    @Test
    @DisplayName("Debería crear un cliente exitosamente")
    void deberiaCrearClienteExitosamente() throws Exception {
        // Arrange
        when(clienteMapper.toDomain(any(ClienteRequest.class))).thenReturn(cliente);
        when(clienteUseCase.crearCliente(any(Cliente.class))).thenReturn(cliente);
        when(clienteMapper.toResponse(any(Cliente.class))).thenReturn(clienteResponse);
        
        // Act & Assert
        mockMvc.perform(post("/clientes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(clienteRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nombre").value("Jose Lema"))
                .andExpect(jsonPath("$.clienteId").value("CLI001"));
                
        verify(clienteUseCase).crearCliente(any(Cliente.class));
    }
    
    @Test
    @DisplayName("Debería obtener un cliente por ID")
    void deberiaObtenerClientePorId() throws Exception {
        // Arrange
        when(clienteUseCase.obtenerClientePorId(1L)).thenReturn(cliente);
        when(clienteMapper.toResponse(any(Cliente.class))).thenReturn(clienteResponse);
        
        // Act & Assert
        mockMvc.perform(get("/clientes/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nombre").value("Jose Lema"));
                
        verify(clienteUseCase).obtenerClientePorId(1L);
    }
    
    @Test
    @DisplayName("Debería obtener todos los clientes")
    void deberiaObtenerTodosLosClientes() throws Exception {
        // Arrange
        List<Cliente> clientes = Arrays.asList(cliente);
        when(clienteUseCase.obtenerTodosLosClientes()).thenReturn(clientes);
        when(clienteMapper.toResponse(any(Cliente.class))).thenReturn(clienteResponse);
       
        // Act & Assert
        mockMvc.perform(get("/clientes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].nombre").value("Jose Lema"));
                
        verify(clienteUseCase).obtenerTodosLosClientes();
    }
    
    @Test
    @DisplayName("Debería actualizar un cliente exitosamente")
    void deberiaActualizarClienteExitosamente() throws Exception {
        // Arrange
        when(clienteMapper.toDomain(any(ClienteRequest.class))).thenReturn(cliente);
        when(clienteUseCase.actualizarCliente(eq(1L), any(Cliente.class))).thenReturn(cliente);
        when(clienteMapper.toResponse(any(Cliente.class))).thenReturn(clienteResponse);
        
        // Act & Assert
        mockMvc.perform(put("/clientes/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(clienteRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nombre").value("Jose Lema"));
                
        verify(clienteUseCase).actualizarCliente(eq(1L), any(Cliente.class));
    }
    
    @Test
    @DisplayName("Debería eliminar un cliente exitosamente")
    void deberiaEliminarClienteExitosamente() throws Exception {
        // Act & Assert
        mockMvc.perform(delete("/clientes/1"))
                .andExpect(status().isNoContent());
                
        verify(clienteUseCase).eliminarCliente(1L);
    }
}
