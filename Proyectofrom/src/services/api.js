import axios from 'axios';

const API_BASE_URL = 'http://localhost:8080/api';

const api = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});

// Interceptor para manejar errores
api.interceptors.response.use(
  (response) => response,
  (error) => {
    if (error.response) {
      const message = error.response.data?.message || 'Error en la petición';
      throw new Error(message);
    }
    throw error;
  }
);

export const clienteService = {
  getAll: () => api.get('/clientes'),
  getById: (id) => api.get(`/clientes/${id}`),
  create: (data) => api.post('/clientes', data),
  update: (id, data) => api.put(`/clientes/${id}`, data),
  delete: (id) => api.delete(`/clientes/${id}`),
};

export const cuentaService = {
  getAll: () => api.get('/cuentas'),
  getById: (id) => api.get(`/cuentas/${id}`),
  getByCliente: (clienteId) => api.get(`/cuentas/cliente/${clienteId}`),
  create: (data) => api.post('/cuentas', data),
  update: (id, data) => api.put(`/cuentas/${id}`, data),
  delete: (id) => api.delete(`/cuentas/${id}`),
};

export const movimientoService = {
  getAll: () => api.get('/movimientos'),
  getById: (id) => api.get(`/movimientos/${id}`),
  getByCuenta: (cuentaId) => api.get(`/movimientos/cuenta/${cuentaId}`),
  create: (data) => api.post('/movimientos', data),
  delete: (id) => api.delete(`/movimientos/${id}`),
};

export const reporteService = {
  generarReporte: (clienteId, fechaInicio, fechaFin) =>
    api.get('/reportes', {
      params: {
        clienteId,
        fechaInicio: fechaInicio.toISOString(),
        fechaFin: fechaFin.toISOString(),
      },
    }),
  generarReportePdf: (clienteId, fechaInicio, fechaFin) =>
    api.get('/reportes/pdf', {
      params: {
        clienteId,
        fechaInicio: fechaInicio.toISOString(),
        fechaFin: fechaFin.toISOString(),
      },
    }),
};

export default api;
