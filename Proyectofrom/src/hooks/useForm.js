import { useState } from 'react';

/**
 * Hook personalizado para manejar formularios en React
 * 
 * @param {Object} initialState - Estado inicial del formulario
 * @param {Function} validate - Función de validación que recibe el formulario y retorna un objeto de errores
 * @returns {Object} Objeto con formData, errors, handleChange, handleSubmit
 * 
 * @example
 * const initialState = { email: '', password: '' };
 * const validate = (form) => {
 *   const errors = {};
 *   if (!form.email) errors.email = 'Email es requerido';
 *   return errors;
 * };
 * const { formData, errors, handleChange, handleSubmit } = useForm(initialState, validate);
 */
export const useForm = (initialState = {}, validate = () => ({})) => {
  // Estado para almacenar los datos del formulario
  const [formData, setFormData] = useState(initialState);
  
  // Estado para almacenar los errores de validación
  const [errors, setErrors] = useState({});

  /**
   * Maneja los cambios en los campos del formulario
   * Actualiza el estado del formulario cuando el usuario escribe
   * 
   * @param {Event} e - Evento del input (onChange)
   */
  const handleChange = (e) => {
    const { name, value } = e.target;
    
    // Actualiza el estado del formulario con el nuevo valor
    setFormData(prev => ({
      ...prev,
      [name]: value
    }));

    // Limpia el error del campo cuando el usuario empieza a escribir
    if (errors[name]) {
      setErrors(prev => ({
        ...prev,
        [name]: ''
      }));
    }
  };

  /**
   * Valida el formulario y retorna true si es válido, false si tiene errores
   * 
   * @returns {boolean} true si el formulario es válido, false si tiene errores
   */
  const handleSubmit = () => {
    // Ejecuta la función de validación pasada como parámetro
    const newErrors = validate(formData);
    
    // Actualiza el estado de errores
    setErrors(newErrors);
    
    // Retorna true si no hay errores (el objeto de errores está vacío)
    // Object.keys(newErrors).length === 0 significa que no hay errores
    return Object.keys(newErrors).length === 0;
  };

  /**
   * Resetea el formulario a su estado inicial
   */
  const reset = () => {
    setFormData(initialState);
    setErrors({});
  };

  // Retornamos todo lo que necesitamos usar en el componente
  return {
    formData,      // Los datos actuales del formulario
    errors,        // Los errores de validación
    handleChange, // Función para manejar cambios en los inputs
    handleSubmit, // Función para validar el formulario
    reset         // Función para resetear el formulario (opcional)
  };
};
