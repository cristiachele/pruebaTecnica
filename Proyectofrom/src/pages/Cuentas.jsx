import React, { useState, useEffect } from 'react';
import { cuentaService, clienteService } from '../services/api';
import './PageStyles.css';

const Cuentas = () => {
  const [cuentas, setCuentas] = useState([]);
  const [clientes, setClientes] = useState([]);
  const [filteredCuentas, setFilteredCuentas] = useState([]);
  const [searchTerm, setSearchTerm] = useState('');
  const [showForm, setShowForm] = useState(false);
  const [editingCuenta, setEditingCuenta] = useState(null);
  const [formData, setFormData] = useState({
    numeroCuenta: '',
    tipoCuenta: '',
    saldoInicial: '',
    estado: true,
    clienteId: '',
  });
  const [errors, setErrors] = useState({});
  const [message, setMessage] = useState('');

  useEffect(() => {
    loadCuentas();
    loadClientes();
  }, []);

  useEffect(() => {
    const filtered = cuentas.filter((cuenta) =>
      Object.values(cuenta).some((value) =>
        String(value).toLowerCase().includes(searchTerm.toLowerCase())
      )
    );
    setFilteredCuentas(filtered);
  }, [searchTerm, cuentas]);

  const loadCuentas = async () => {
    try {
      const response = await cuentaService.getAll();
      setCuentas(response.data);
      setFilteredCuentas(response.data);
    } catch (error) {
      setMessage('Error al cargar cuentas: ' + error.message);
    }
  };

  const loadClientes = async () => {
    try {
      const response = await clienteService.getAll();
      setClientes(response.data);
    } catch (error) {
      console.error('Error al cargar clientes:', error);
    }
  };

  const validateForm = () => {
    const newErrors = {};
    if (!formData.numeroCuenta) newErrors.numeroCuenta = 'El número de cuenta es obligatorio';
    if (!formData.tipoCuenta) newErrors.tipoCuenta = 'El tipo de cuenta es obligatorio';
    if (formData.saldoInicial === '') newErrors.saldoInicial = 'El saldo inicial es obligatorio';
    if (!formData.clienteId) newErrors.clienteId = 'El cliente es obligatorio';
    setErrors(newErrors);
    return Object.keys(newErrors).length === 0;
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!validateForm()) return;

    try {
      const data = {
        ...formData,
        saldoInicial: parseFloat(formData.saldoInicial),
        clienteId: parseInt(formData.clienteId),
      };
      if (editingCuenta) {
        await cuentaService.update(editingCuenta.id, data);
        setMessage('Cuenta actualizada exitosamente');
      } else {
        await cuentaService.create(data);
        setMessage('Cuenta creada exitosamente');
      }
      resetForm();
      loadCuentas();
    } catch (error) {
      setMessage('Error: ' + error.message);
    }
  };

  const handleEdit = (cuenta) => {
    setEditingCuenta(cuenta);
    setFormData({
      numeroCuenta: cuenta.numeroCuenta || '',
      tipoCuenta: cuenta.tipoCuenta || '',
      saldoInicial: cuenta.saldoInicial || '',
      estado: cuenta.estado !== undefined ? cuenta.estado : true,
      clienteId: cuenta.clienteId || '',
    });
    setShowForm(true);
    setErrors({});
  };

  const handleDelete = async (id) => {
    if (window.confirm('¿Está seguro de eliminar esta cuenta?')) {
      try {
        await cuentaService.delete(id);
        setMessage('Cuenta eliminada exitosamente');
        loadCuentas();
      } catch (error) {
        setMessage('Error: ' + error.message);
      }
    }
  };

  const resetForm = () => {
    setFormData({
      numeroCuenta: '',
      tipoCuenta: '',
      saldoInicial: '',
      estado: true,
      clienteId: '',
    });
    setEditingCuenta(null);
    setShowForm(false);
    setErrors({});
  };

  const getClienteNombre = (clienteId) => {
    const cliente = clientes.find((c) => c.id === clienteId);
    return cliente ? cliente.nombre : clienteId;
  };

  return (
    <div className="page-container">
      <h1>Cuentas</h1>
      
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
          {showForm ? 'Cancelar' : 'Nueva Cuenta'}
        </button>
      </div>

      {showForm && (
        <form className="form" onSubmit={handleSubmit}>
          <div className="form-group">
            <label>Número de Cuenta *</label>
            <input
              type="text"
              value={formData.numeroCuenta}
              onChange={(e) => setFormData({ ...formData, numeroCuenta: e.target.value })}
            />
            {errors.numeroCuenta && <span className="error-text">{errors.numeroCuenta}</span>}
          </div>

          <div className="form-group">
            <label>Tipo de Cuenta *</label>
            <select
              value={formData.tipoCuenta}
              onChange={(e) => setFormData({ ...formData, tipoCuenta: e.target.value })}
            >
              <option value="">Seleccione...</option>
              <option value="Ahorro">Ahorro</option>
              <option value="Corriente">Corriente</option>
            </select>
            {errors.tipoCuenta && <span className="error-text">{errors.tipoCuenta}</span>}
          </div>

          <div className="form-group">
            <label>Saldo Inicial *</label>
            <input
              type="number"
              step="0.01"
              value={formData.saldoInicial}
              onChange={(e) => setFormData({ ...formData, saldoInicial: e.target.value })}
            />
            {errors.saldoInicial && <span className="error-text">{errors.saldoInicial}</span>}
          </div>

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
            <label>
              <input
                type="checkbox"
                checked={formData.estado}
                onChange={(e) => setFormData({ ...formData, estado: e.target.checked })}
              />
              Estado Activo
            </label>
          </div>

          <div className="form-actions">
            <button type="submit" className="btn btn-primary">
              {editingCuenta ? 'Actualizar' : 'Crear'}
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
            <th>Número de Cuenta</th>
            <th>Tipo</th>
            <th>Saldo Inicial</th>
            <th>Cliente</th>
            <th>Estado</th>
            <th>Acciones</th>
          </tr>
        </thead>
        <tbody>
          {filteredCuentas.length === 0 ? (
            <tr>
              <td colSpan="7" className="no-data">No hay cuentas registradas</td>
            </tr>
          ) : (
            filteredCuentas.map((cuenta) => (
              <tr key={cuenta.id}>
                <td>{cuenta.id}</td>
                <td>{cuenta.numeroCuenta}</td>
                <td>{cuenta.tipoCuenta}</td>
                <td>${cuenta.saldoInicial.toFixed(2)}</td>
                <td>{getClienteNombre(cuenta.clienteId)}</td>
                <td>{cuenta.estado ? 'Activa' : 'Inactiva'}</td>
                <td>
                  <button className="btn btn-sm btn-edit" onClick={() => handleEdit(cuenta)}>
                    Editar
                  </button>
                  <button className="btn btn-sm btn-delete" onClick={() => handleDelete(cuenta.id)}>
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

export default Cuentas;
