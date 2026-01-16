import React, { useState, useEffect } from 'react';
import { reporteService, clienteService } from '../services/api';
import './PageStyles.css';

const Reportes = () => {
  const [clientes, setClientes] = useState([]);
  const [reporte, setReporte] = useState(null);
  const [loading, setLoading] = useState(false);
  const [formData, setFormData] = useState({
    clienteId: '',
    fechaInicio: '',
    fechaFin: '',
  });
  const [errors, setErrors] = useState({});
  const [message, setMessage] = useState('');

  useEffect(() => {
    loadClientes();
  }, []);

  const loadClientes = async () => {
    try {
      const response = await clienteService.getAll();
      setClientes(response.data);
    } catch (error) {
      setMessage('Error al cargar clientes: ' + error.message);
    }
  };

  const validateForm = () => {
    const newErrors = {};
    if (!formData.clienteId) newErrors.clienteId = 'El cliente es obligatorio';
    if (!formData.fechaInicio) newErrors.fechaInicio = 'La fecha de inicio es obligatoria';
    if (!formData.fechaFin) newErrors.fechaFin = 'La fecha de fin es obligatoria';
    if (formData.fechaInicio && formData.fechaFin && formData.fechaInicio > formData.fechaFin) {
      newErrors.fechaFin = 'La fecha de fin debe ser posterior a la fecha de inicio';
    }
    setErrors(newErrors);
    return Object.keys(newErrors).length === 0;
  };

  const handleGenerarReporte = async () => {
    if (!validateForm()) return;

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
    if (!validateForm()) return;

    setLoading(true);
    try {
      const fechaInicio = new Date(formData.fechaInicio);
      const fechaFin = new Date(formData.fechaFin);
      fechaFin.setHours(23, 59, 59, 999);

      const response = await reporteService.generarReportePdf(
        parseInt(formData.clienteId),
        fechaInicio,
        fechaFin
      );

      // Convertir base64 a blob y descargar
      const pdfBase64 = response.data.pdf;
      const byteCharacters = atob(pdfBase64);
      const byteNumbers = new Array(byteCharacters.length);
      for (let i = 0; i < byteCharacters.length; i++) {
        byteNumbers[i] = byteCharacters.charCodeAt(i);
      }
      const byteArray = new Uint8Array(byteNumbers);
      const blob = new Blob([byteArray], { type: 'application/pdf' });

      const url = window.URL.createObjectURL(blob);
      const link = document.createElement('a');
      link.href = url;
      link.download = `reporte_${formData.clienteId}_${formData.fechaInicio}_${formData.fechaFin}.pdf`;
      document.body.appendChild(link);
      link.click();
      document.body.removeChild(link);
      window.URL.revokeObjectURL(url);

      setMessage('PDF descargado exitosamente');
    } catch (error) {
      setMessage('Error al descargar PDF: ' + error.message);
    } finally {
      setLoading(false);
    }
  };

  const getClienteNombre = (clienteId) => {
    const cliente = clientes.find((c) => c.id === clienteId);
    return cliente ? cliente.nombre : clienteId;
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
            onChange={(e) => setFormData({ ...formData, clienteId: e.target.value })}
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
            onChange={(e) => setFormData({ ...formData, fechaInicio: e.target.value })}
          />
          {errors.fechaInicio && <span className="error-text">{errors.fechaInicio}</span>}
        </div>

        <div className="form-group">
          <label>Fecha de Fin *</label>
          <input
            type="date"
            value={formData.fechaFin}
            onChange={(e) => setFormData({ ...formData, fechaFin: e.target.value })}
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
