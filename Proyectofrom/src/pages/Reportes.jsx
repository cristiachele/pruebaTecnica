import React, { useState, useEffect } from 'react';
import { reporteService, clienteService } from '../services/api';
import { useForm } from '../hooks/useForm'; // Hook personalizado para formularios
import { downloadPdfFromBase64 } from '../utils/fileDownloader'; // Utilidad de descarga
import './PageStyles.css';

// Hook personalizado para la lógica de la página
const useReportes = () => {
  const [clientes, setClientes] = useState([]);
  const [message, setMessage] = useState('');

  useEffect(() => {
    const loadClientes = async () => {
      try {
        const response = await clienteService.getAll();
        setClientes(response.data);
      } catch (error) {
        setMessage('Error al cargar clientes: ' + error.message);
      }
    };
    loadClientes();
  }, []);

  return { clientes, message, setMessage };
};

const Reportes = () => {
  const [reporte, setReporte] = useState(null);
  const [loading, setLoading] = useState(false);

  const { clientes, message, setMessage } = useReportes();

  const initialState = {
    clienteId: '',
    fechaInicio: '',
    fechaFin: '',
  };

  const validate = (form) => {
    const newErrors = {};
    if (!form.clienteId) newErrors.clienteId = 'El cliente es obligatorio';
    if (!form.fechaInicio) newErrors.fechaInicio = 'La fecha de inicio es obligatoria';
    if (!form.fechaFin) newErrors.fechaFin = 'La fecha de fin es obligatoria';
    if (form.fechaInicio && form.fechaFin && form.fechaInicio > form.fechaFin) {
      newErrors.fechaFin = 'La fecha de fin debe ser posterior a la fecha de inicio';
    }
    return newErrors;
  };

  const { formData, errors, handleChange, handleSubmit } = useForm(initialState, validate);

  const handleGenerarReporte = async () => {
    if (!handleSubmit()) return;

    setLoading(true);
    setMessage('');
    try {
      const fechaInicio = new Date(formData.fechaInicio);
      const fechaFin = new Date(formData.fechaFin);
      fechaFin.setHours(23, 59, 59, 999); // Incluir todo el día

      const response = await reporteService.generarReporte(
        parseInt(formData.clienteId),
        fechaInicio,
        fechaFin
      );
      setReporte(response.data);
    } catch (error) {
      setMessage('Error al generar reporte: ' + error.message);
    } finally {
      setLoading(false);
    }
  };

  const handleDescargarPdf = async () => {
    if (!handleSubmit()) return;

    setLoading(true);
    setMessage('');
    try {
      const fechaInicio = new Date(formData.fechaInicio);
      const fechaFin = new Date(formData.fechaFin);
      fechaFin.setHours(23, 59, 59, 999);

      const response = await reporteService.generarReportePdf(
        parseInt(formData.clienteId), fechaInicio, fechaFin
      );

      const fileName = `reporte_${formData.clienteId}_${formData.fechaInicio}_${formData.fechaFin}.pdf`;
      downloadPdfFromBase64(response.data.pdf, fileName);

      setMessage('PDF descargado exitosamente');
    } catch (error) {
      setMessage('Error al descargar el PDF: ' + error.message);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="page-container">
      <h1>Reportes</h1>
      
      {message && (
        <div className={`message ${message.includes('Error') ? 'error' : 'success'}`}>
          {message}
        </div>
      )}

      <div className="report-form">
        <h2>Generar Reporte de Estado de Cuenta</h2>
        
        <div className="form-group">
          <label>Cliente *</label>
          <select
            value={formData.clienteId}
            name="clienteId"
            onChange={handleChange}
          >
            <option value="">Seleccione un cliente...</option>
            {clientes.map((cliente) => (
              <option key={cliente.id} value={cliente.id}>
                {cliente.nombre} ({cliente.clienteId})
              </option>
            ))}
          </select>
          {errors.clienteId && <span className="error-text">{errors.clienteId}</span>}
        </div>

        <div className="form-group">
          <label>Fecha de Inicio *</label>
          <input
            type="date"
            value={formData.fechaInicio}
            name="fechaInicio"
            onChange={handleChange}
          />
          {errors.fechaInicio && <span className="error-text">{errors.fechaInicio}</span>}
        </div>

        <div className="form-group">
          <label>Fecha de Fin *</label>
          <input
            type="date"
            value={formData.fechaFin}
            name="fechaFin"
            onChange={handleChange}
          />
          {errors.fechaFin && <span className="error-text">{errors.fechaFin}</span>}
        </div>

        <div className="form-actions">
          <button
            type="button"
            className="btn btn-primary"
            onClick={handleGenerarReporte}
            disabled={loading}
          >
            {loading ? 'Generando...' : 'Generar Reporte'}
          </button>
          <button
            type="button"
            className="btn btn-secondary"
            onClick={handleDescargarPdf}
            disabled={loading || !reporte}
          >
            Descargar PDF
          </button>
        </div>
      </div>

      {reporte && (
        <div className="report-content">
          <h2>Reporte de Estado de Cuenta</h2>
          <div className="report-header">
            <p><strong>Cliente:</strong> {reporte.clienteNombre}</p>
            <p><strong>Período:</strong> {reporte.fechaInicio} - {reporte.fechaFin}</p>
          </div>

          {reporte.cuentas && reporte.cuentas.length > 0 ? (
            reporte.cuentas.map((cuenta, index) => (
              <div key={index} className="cuenta-section">
                <h3>Cuenta: {cuenta.numeroCuenta} - {cuenta.tipoCuenta}</h3>
                <p>Saldo Inicial: ${cuenta.saldoInicial.toFixed(2)}</p>
                <p>Saldo Actual: ${cuenta.saldoActual.toFixed(2)}</p>
                <p>Estado: {cuenta.estado ? 'Activa' : 'Inactiva'}</p>

                {cuenta.movimientos && cuenta.movimientos.length > 0 && (
                  <table className="data-table">
                    <thead>
                      <tr>
                        <th>Fecha</th>
                        <th>Tipo</th>
                        <th>Valor</th>
                        <th>Saldo</th>
                      </tr>
                    </thead>
                    <tbody>
                      {cuenta.movimientos.map((mov, idx) => (
                        <tr key={idx}>
                          <td>{mov.fecha}</td>
                          <td>{mov.tipoMovimiento}</td>
                          <td className={mov.valor >= 0 ? 'positive' : 'negative'}>
                            ${mov.valor.toFixed(2)}
                          </td>
                          <td>${mov.saldo.toFixed(2)}</td>
                        </tr>
                      ))}
                    </tbody>
                  </table>
                )}
              </div>
            ))
          ) : (
            <p>No hay movimientos en el período seleccionado</p>
          )}

          <div className="report-totals">
            <p><strong>Total Créditos:</strong> ${reporte.totalCreditos.toFixed(2)}</p>
            <p><strong>Total Débitos:</strong> ${reporte.totalDebitos.toFixed(2)}</p>
          </div>
        </div>
      )}
    </div>
  );
};

export default Reportes;
