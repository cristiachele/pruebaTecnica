import React, { useState, useEffect } from 'react';
import { movimientoService, cuentaService } from '../services/api';
import './PageStyles.css';

const Movimientos = () => {
  const [movimientos, setMovimientos] = useState([]);
  const [cuentas, setCuentas] = useState([]);
  const [filteredMovimientos, setFilteredMovimientos] = useState([]);
  const [searchTerm, setSearchTerm] = useState('');
  const [showForm, setShowForm] = useState(false);
  const [formData, setFormData] = useState({
    tipoMovimiento: '',
    valor: '',
    cuentaId: '',
  });
  const [errors, setErrors] = useState({});
  const [message, setMessage] = useState('');

  useEffect(() => {
    loadMovimientos();
    loadCuentas();
  }, []);

  useEffect(() => {
    const filtered = movimientos.filter((movimiento) =>
      Object.values(movimiento).some((value) =>
        String(value).toLowerCase().includes(searchTerm.toLowerCase())
      )
    );
    setFilteredMovimientos(filtered);
  }, [searchTerm, movimientos]);

  const loadMovimientos = async () => {
    try {
      const response = await movimientoService.getAll();
      setMovimientos(response.data);
      setFilteredMovimientos(response.data);
    } catch (error) {
      setMessage('Error al cargar movimientos: ' + error.message);
    }
  };

  const loadCuentas = async () => {
    try {
      const response = await cuentaService.getAll();
      setCuentas(response.data);
    } catch (error) {
      console.error('Error al cargar cuentas:', error);
    }
  };

  const validateForm = () => {
    const newErrors = {};
    if (!formData.tipoMovimiento) newErrors.tipoMovimiento = 'El tipo de movimiento es obligatorio';
    if (!formData.valor || formData.valor <= 0) newErrors.valor = 'El valor debe ser mayor a 0';
    if (!formData.cuentaId) newErrors.cuentaId = 'La cuenta es obligatoria';
    setErrors(newErrors);
    return Object.keys(newErrors).length === 0;
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!validateForm()) return;

    try {
      const data = {
        ...formData,
        valor: parseFloat(formData.valor),
        cuentaId: parseInt(formData.cuentaId),
      };
      await movimientoService.create(data);
      setMessage('Movimiento creado exitosamente');
      resetForm();
      loadMovimientos();
    } catch (error) {
      setMessage('Error: ' + error.message);
    }
  };

  const handleDelete = async (id) => {
    if (window.confirm('¿Está seguro de eliminar este movimiento?')) {
      try {
        await movimientoService.delete(id);
        setMessage('Movimiento eliminado exitosamente');
        loadMovimientos();
      } catch (error) {
        setMessage('Error: ' + error.message);
      }
    }
  };

  const resetForm = () => {
    setFormData({
      tipoMovimiento: '',
      valor: '',
      cuentaId: '',
    });
    setShowForm(false);
    setErrors({});
  };

  const getCuentaNumero = (cuentaId) => {
    const cuenta = cuentas.find((c) => c.id === cuentaId);
    return cuenta ? cuenta.numeroCuenta : cuentaId;
  };

  const formatDate = (dateString) => {
    if (!dateString) return '';
    const date = new Date(dateString);
    return date.toLocaleString('es-ES');
  };

  return (
    <div className="page-container">
      <h1>Movimientos</h1>
      
      {message && (
        <div className={`message ${message.includes('Error') ? 'error' : 'success'}`}>
          {message}
        </div>
      )}

      <div className="page-header">
        <input
          type="text"
          placeholder="Buscar..."
          className="search-input"
          value={searchTerm}
          onChange={(e) => setSearchTerm(e.target.value)}
        />
        <button className="btn btn-primary" onClick={() => setShowForm(!showForm)}>
          {showForm ? 'Cancelar' : 'Nuevo Movimiento'}
        </button>
      </div>

      {showForm && (
        <form className="form" onSubmit={handleSubmit}>
          <div className="form-group">
            <label>Tipo de Movimiento *</label>
            <select
              value={formData.tipoMovimiento}
              onChange={(e) => setFormData({ ...formData, tipoMovimiento: e.target.value })}
            >
              <option value="">Seleccione...</option>
              <option value="Crédito">Crédito (Depósito)</option>
              <option value="Débito">Débito (Retiro)</option>
            </select>
            {errors.tipoMovimiento && <span className="error-text">{errors.tipoMovimiento}</span>}
          </div>

          <div className="form-group">
            <label>Valor *</label>
            <input
              type="number"
              step="0.01"
              min="0.01"
              value={formData.valor}
              onChange={(e) => setFormData({ ...formData, valor: e.target.value })}
            />
            {errors.valor && <span className="error-text">{errors.valor}</span>}
          </div>

          <div className="form-group">
            <label>Cuenta *</label>
            <select
              value={formData.cuentaId}
              onChange={(e) => setFormData({ ...formData, cuentaId: e.target.value })}
            >
              <option value="">Seleccione una cuenta...</option>
              {cuentas.map((cuenta) => (
                <option key={cuenta.id} value={cuenta.id}>
                  {cuenta.numeroCuenta} - {cuenta.tipoCuenta} (Saldo: ${cuenta.saldoInicial.toFixed(2)})
                </option>
              ))}
            </select>
            {errors.cuentaId && <span className="error-text">{errors.cuentaId}</span>}
          </div>

          <div className="form-actions">
            <button type="submit" className="btn btn-primary">
              Crear Movimiento
            </button>
            <button type="button" className="btn btn-secondary" onClick={resetForm}>
              Cancelar
            </button>
          </div>
        </form>
      )}

      <table className="data-table">
        <thead>
          <tr>
            <th>ID</th>
            <th>Fecha</th>
            <th>Tipo</th>
            <th>Valor</th>
            <th>Saldo</th>
            <th>Cuenta</th>
            <th>Acciones</th>
          </tr>
        </thead>
        <tbody>
          {filteredMovimientos.length === 0 ? (
            <tr>
              <td colSpan="7" className="no-data">No hay movimientos registrados</td>
            </tr>
          ) : (
            filteredMovimientos.map((movimiento) => (
              <tr key={movimiento.id}>
                <td>{movimiento.id}</td>
                <td>{formatDate(movimiento.fecha)}</td>
                <td>
                  <span className={`badge ${movimiento.tipoMovimiento === 'Crédito' ? 'badge-success' : 'badge-danger'}`}>
                    {movimiento.tipoMovimiento}
                  </span>
                </td>
                <td className={movimiento.valor >= 0 ? 'positive' : 'negative'}>
                  ${movimiento.valor.toFixed(2)}
                </td>
                <td>${movimiento.saldo.toFixed(2)}</td>
                <td>{getCuentaNumero(movimiento.cuentaId)}</td>
                <td>
                  <button className="btn btn-sm btn-delete" onClick={() => handleDelete(movimiento.id)}>
                    Eliminar
                  </button>
                </td>
              </tr>
            ))
          )}
        </tbody>
      </table>
    </div>
  );
};

export default Movimientos;
