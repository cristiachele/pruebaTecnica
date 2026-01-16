import React, { useState, useEffect } from 'react';
import { clienteService } from '../services/api';
import './PageStyles.css';

const Clientes = () => {
  const [clientes, setClientes] = useState([]);
  const [filteredClientes, setFilteredClientes] = useState([]);
  const [searchTerm, setSearchTerm] = useState('');
  const [showForm, setShowForm] = useState(false);
  const [editingCliente, setEditingCliente] = useState(null);
  const [formData, setFormData] = useState({
    nombre: '',
    genero: '',
    edad: '',
    identificacion: '',
    direccion: '',
    telefono: '',
    clienteId: '',
    contrasena: '',
    estado: true,
  });
  const [errors, setErrors] = useState({});
  const [message, setMessage] = useState('');

  useEffect(() => {
    loadClientes();
  }, []);

  useEffect(() => {
    const filtered = clientes.filter((cliente) =>
      Object.values(cliente).some((value) =>
        String(value).toLowerCase().includes(searchTerm.toLowerCase())
      )
    );
    setFilteredClientes(filtered);
  }, [searchTerm, clientes]);

  const loadClientes = async () => {
    try {
      const response = await clienteService.getAll();
      setClientes(response.data);
      setFilteredClientes(response.data);
    } catch (error) {
      setMessage('Error al cargar clientes: ' + error.message);
    }
  };

  const validateForm = () => {
    const newErrors = {};
    if (!formData.nombre) newErrors.nombre = 'El nombre es obligatorio';
    if (!formData.identificacion) newErrors.identificacion = 'La identificación es obligatoria';
    if (!formData.clienteId) newErrors.clienteId = 'El clienteId es obligatorio';
    if (!formData.contrasena) newErrors.contrasena = 'La contraseña es obligatoria';
    setErrors(newErrors);
    return Object.keys(newErrors).length === 0;
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!validateForm()) return;

    try {
      if (editingCliente) {
        await clienteService.update(editingCliente.id, formData);
        setMessage('Cliente actualizado exitosamente');
      } else {
        await clienteService.create(formData);
        setMessage('Cliente creado exitosamente');
      }
      resetForm();
      loadClientes();
    } catch (error) {
      setMessage('Error: ' + error.message);
    }
  };

  const handleEdit = (cliente) => {
    setEditingCliente(cliente);
    setFormData({
      nombre: cliente.nombre || '',
      genero: cliente.genero || '',
      edad: cliente.edad || '',
      identificacion: cliente.identificacion || '',
      direccion: cliente.direccion || '',
      telefono: cliente.telefono || '',
      clienteId: cliente.clienteId || '',
      contrasena: '',
      estado: cliente.estado !== undefined ? cliente.estado : true,
    });
    setShowForm(true);
    setErrors({});
  };

  const handleDelete = async (id) => {
    if (window.confirm('¿Está seguro de eliminar este cliente?')) {
      try {
        await clienteService.delete(id);
        setMessage('Cliente eliminado exitosamente');
        loadClientes();
      } catch (error) {
        setMessage('Error: ' + error.message);
      }
    }
  };

  const resetForm = () => {
    setFormData({
      nombre: '',
      genero: '',
      edad: '',
      identificacion: '',
      direccion: '',
      telefono: '',
      clienteId: '',
      contrasena: '',
      estado: true,
    });
    setEditingCliente(null);
    setShowForm(false);
    setErrors({});
  };

  return (
    <div className="page-container">
      <h1>Clientes</h1>
      
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
          {showForm ? 'Cancelar' : 'Nuevo Cliente'}
        </button>
      </div>

      {showForm && (
        <form className="form" onSubmit={handleSubmit}>
          <div className="form-group">
            <label>Nombre *</label>
            <input
              type="text"
              value={formData.nombre}
              onChange={(e) => setFormData({ ...formData, nombre: e.target.value })}
            />
            {errors.nombre && <span className="error-text">{errors.nombre}</span>}
          </div>

          <div className="form-group">
            <label>Género</label>
            <input
              type="text"
              value={formData.genero}
              onChange={(e) => setFormData({ ...formData, genero: e.target.value })}
            />
          </div>

          <div className="form-group">
            <label>Edad</label>
            <input
              type="number"
              value={formData.edad}
              onChange={(e) => setFormData({ ...formData, edad: e.target.value })}
            />
          </div>

          <div className="form-group">
            <label>Identificación *</label>
            <input
              type="text"
              value={formData.identificacion}
              onChange={(e) => setFormData({ ...formData, identificacion: e.target.value })}
            />
            {errors.identificacion && <span className="error-text">{errors.identificacion}</span>}
          </div>

          <div className="form-group">
            <label>Dirección</label>
            <input
              type="text"
              value={formData.direccion}
              onChange={(e) => setFormData({ ...formData, direccion: e.target.value })}
            />
          </div>

          <div className="form-group">
            <label>Teléfono</label>
            <input
              type="text"
              value={formData.telefono}
              onChange={(e) => setFormData({ ...formData, telefono: e.target.value })}
            />
          </div>

          <div className="form-group">
            <label>Cliente ID *</label>
            <input
              type="text"
              value={formData.clienteId}
              onChange={(e) => setFormData({ ...formData, clienteId: e.target.value })}
            />
            {errors.clienteId && <span className="error-text">{errors.clienteId}</span>}
          </div>

          <div className="form-group">
            <label>Contraseña *</label>
            <input
              type="password"
              value={formData.contrasena}
              onChange={(e) => setFormData({ ...formData, contrasena: e.target.value })}
            />
            {errors.contrasena && <span className="error-text">{errors.contrasena}</span>}
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
              {editingCliente ? 'Actualizar' : 'Crear'}
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
            <th>Nombre</th>
            <th>Identificación</th>
            <th>Cliente ID</th>
            <th>Teléfono</th>
            <th>Estado</th>
            <th>Acciones</th>
          </tr>
        </thead>
        <tbody>
          {filteredClientes.length === 0 ? (
            <tr>
              <td colSpan="7" className="no-data">No hay clientes registrados</td>
            </tr>
          ) : (
            filteredClientes.map((cliente) => (
              <tr key={cliente.id}>
                <td>{cliente.id}</td>
                <td>{cliente.nombre}</td>
                <td>{cliente.identificacion}</td>
                <td>{cliente.clienteId}</td>
                <td>{cliente.telefono}</td>
                <td>{cliente.estado ? 'Activo' : 'Inactivo'}</td>
                <td>
                  <button className="btn btn-sm btn-edit" onClick={() => handleEdit(cliente)}>
                    Editar
                  </button>
                  <button className="btn btn-sm btn-delete" onClick={() => handleDelete(cliente.id)}>
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

export default Clientes;
