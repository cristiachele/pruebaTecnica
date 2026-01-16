package ec.com.dinersclub.proyectobancario.adapter.input.web.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import ec.com.dinersclub.proyectobancario.adapter.input.web.dto.MovimientoRequest;
import ec.com.dinersclub.proyectobancario.adapter.input.web.dto.MovimientoResponse;
import ec.com.dinersclub.proyectobancario.adapter.input.web.mapper.MovimientoMapper;
import ec.com.dinersclub.proyectobancario.application.usecase.MovimientoUseCase;
import ec.com.dinersclub.proyectobancario.domain.model.Movimiento;

/**
 * Pruebas unitarias para MovimientoController.
 * 
 * @author Sistema Bancario
 * @version 1.0.0
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("Pruebas del controlador de movimientos")
class MovimientoControllerTest {
    
    @Mock
    private MovimientoUseCase movimientoUseCase;
    
    @Mock
    private MovimientoMapper movimientoMapper;
    
    @InjectMocks
    private MovimientoController movimientoController;
    
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private MovimientoRequest movimientoRequest;
    private Movimiento movimiento;
    private MovimientoResponse movimientoResponse;
    
    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(movimientoController).build();
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        
        movimientoRequest = new MovimientoRequest();
        movimientoRequest.setTipoMovimiento("Crédito");
        movimientoRequest.setValor(new BigDecimal("600.0"));
        movimientoRequest.setCuentaId(1L);
        
        movimiento = new Movimiento();
        movimiento.setId(1L);
        movimiento.setTipoMovimiento("Crédito");
        movimiento.setValor(new BigDecimal("600.0"));
        movimiento.setFecha(LocalDateTime.now());
        movimiento.setSaldo(new BigDecimal("600.0"));
        movimiento.setCuentaId(1L);
        
        movimientoResponse = new MovimientoResponse();
        movimientoResponse.setId(1L);
        movimientoResponse.setTipoMovimiento("Crédito");
        movimientoResponse.setValor(new BigDecimal("600.0"));
        movimientoResponse.setSaldo(new BigDecimal("600.0"));
        movimientoResponse.setFecha(LocalDateTime.now());
        movimientoResponse.setCuentaId(1L);
    }
    
    @Test
    @DisplayName("Debería crear un movimiento exitosamente")
    void deberiaCrearMovimientoExitosamente() throws Exception {
        // Arrange
        when(movimientoMapper.toDomain(any(MovimientoRequest.class))).thenReturn(movimiento);
        when(movimientoUseCase.crearMovimiento(any(Movimiento.class))).thenReturn(movimiento);
        when(movimientoMapper.toResponse(any(Movimiento.class))).thenReturn(movimientoResponse);
        
        // Act & Assert
        mockMvc.perform(post("/movimientos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(movimientoRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.tipoMovimiento").value("Crédito"))
                .andExpect(jsonPath("$.valor").value(600.0));
                
        verify(movimientoUseCase).crearMovimiento(any(Movimiento.class));
    }
    
    @Test
    @DisplayName("Debería obtener un movimiento por ID")
    void deberiaObtenerMovimientoPorId() throws Exception {
        // Arrange
        when(movimientoUseCase.obtenerMovimientoPorId(1L)).thenReturn(movimiento);
        when(movimientoMapper.toResponse(any(Movimiento.class))).thenReturn(movimientoResponse);
        
        // Act & Assert
        mockMvc.perform(get("/movimientos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.tipoMovimiento").value("Crédito"));
                
        verify(movimientoUseCase).obtenerMovimientoPorId(1L);
    }
    
    @Test
    @DisplayName("Debería obtener todos los movimientos")
    void deberiaObtenerTodosLosMovimientos() throws Exception {
        // Arrange
        List<Movimiento> movimientos = Arrays.asList(movimiento);
        when(movimientoUseCase.obtenerTodosLosMovimientos()).thenReturn(movimientos);
        when(movimientoMapper.toResponse(any(Movimiento.class))).thenReturn(movimientoResponse);
        
        // Act & Assert
        mockMvc.perform(get("/movimientos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].tipoMovimiento").value("Crédito"));
                
        verify(movimientoUseCase).obtenerTodosLosMovimientos();
    }
    
    @Test
    @DisplayName("Debería obtener movimientos por cuenta")
    void deberiaObtenerMovimientosPorCuenta() throws Exception {
        // Arrange
        List<Movimiento> movimientos = Arrays.asList(movimiento);
        when(movimientoUseCase.obtenerMovimientosPorCuenta(1L)).thenReturn(movimientos);
        when(movimientoMapper.toResponse(any(Movimiento.class))).thenReturn(movimientoResponse);
        
        // Act & Assert
        mockMvc.perform(get("/movimientos/cuenta/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].cuentaId").value(1L));
                
        verify(movimientoUseCase).obtenerMovimientosPorCuenta(1L);
    }
    
    @Test
    @DisplayName("Debería obtener movimientos por rango de fechas")
    void deberiaObtenerMovimientosPorRango() throws Exception {
        // Arrange
        List<Movimiento> movimientos = Arrays.asList(movimiento);
        LocalDateTime fechaInicio = LocalDateTime.now().minusDays(1);
        LocalDateTime fechaFin = LocalDateTime.now().plusDays(1);
        
        when(movimientoUseCase.obtenerMovimientosPorCuentaYRango(eq(1L), any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(movimientos);
        when(movimientoMapper.toResponse(any(Movimiento.class))).thenReturn(movimientoResponse);
        
        // Act & Assert
        mockMvc.perform(get("/movimientos/cuenta/1/rango")
                .param("fechaInicio", fechaInicio.toString())
                .param("fechaFin", fechaFin.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
                
        verify(movimientoUseCase).obtenerMovimientosPorCuentaYRango(eq(1L), any(LocalDateTime.class), any(LocalDateTime.class));
    }
    
    @Test
    @DisplayName("Debería eliminar un movimiento exitosamente")
    void deberiaEliminarMovimientoExitosamente() throws Exception {
        // Act & Assert
        mockMvc.perform(delete("/movimientos/1"))
                .andExpect(status().isNoContent());
                
        verify(movimientoUseCase).eliminarMovimiento(1L);
    }
}
