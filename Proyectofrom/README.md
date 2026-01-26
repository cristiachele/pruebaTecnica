# Proyecto Bancario - Frontend

Interfaz de usuario desarrollada con React para el sistema bancario.

## Requisitos Previos

- Node.js 18 o superior
- npm o yarn
- Docker (opcional, para ejecución con contenedores)

## Instalación

### Opción 1: Ejecución Local

1. **Navega al directorio del proyecto:**
   ```bash
   cd Proyectofrom
   ```

2. **Instala las dependencias:**
   ```bash
   npm install
   ```

3. **Inicia el servidor de desarrollo:**
   ```bash
   npm run dev
   ```

   La aplicación estará disponible en `http://localhost:3000`

4. **Asegúrate de que el backend esté ejecutándose en `http://localhost:8080`**

### Opción 2: Ejecución con Docker

1. **Construye la imagen:**
   ```bash
   docker build -t banco-frontend .
   ```

2. **Ejecuta el contenedor:**
   ```bash
   docker run -p 80:80 banco-frontend
   ```

   La aplicación estará disponible en `http://localhost`

## Estructura del Proyecto

```
src/
├── components/          # Componentes reutilizables
│   ├── Layout.jsx       # Layout principal con navegación
│   └── Layout.css
├── pages/               # Páginas principales
│   ├── Clientes.jsx     # CRUD de clientes
│   ├── Cuentas.jsx      # CRUD de cuentas
│   ├── Movimientos.jsx  # CRUD de movimientos
│   ├── Reportes.jsx     # Generación de reportes
│   └── PageStyles.css   # Estilos de las páginas
├── services/            # Servicios de API
│   └── api.js          # Cliente HTTP y servicios
├── test/               # Pruebas
│   └── setup.js
├── App.jsx             # Componente principal
├── main.jsx            # Punto de entrada
└── index.css           # Estilos globales
```

## Funcionalidades

### Clientes
- Crear, editar, eliminar y listar clientes
- Búsqueda rápida de clientes
- Validación de formularios
- Mensajes de error y éxito

### Cuentas
- Crear, editar, eliminar y listar cuentas
- Asociar cuentas a clientes
- Búsqueda rápida de cuentas
- Validación de formularios

### Movimientos
- Crear movimientos (créditos y débitos)
- Listar movimientos por cuenta
- Búsqueda rápida de movimientos
- Visualización de saldos y tipos de movimiento

### Reportes
- Generar reportes de estado de cuenta
- Filtrar por cliente y rango de fechas
- Descargar reportes en formato PDF
- Visualización de movimientos y totales

## Scripts Disponibles

- `npm run dev` - Inicia el servidor de desarrollo
- `npm run build` - Construye la aplicación para producción
- `npm run preview` - Previsualiza la build de producción
- `npm test` - Ejecuta las pruebas unitarias

## Pruebas

### Ejecutar Pruebas Unitarias
```bash
npm test
```

Las pruebas utilizan:
- **Vitest** como framework de pruebas
- **React Testing Library** para pruebas de componentes
- **@testing-library/jest-dom** para matchers adicionales

## Configuración

### API Base URL
Por defecto, la aplicación se conecta a `http://localhost:8080/api`. 

Para cambiar la URL, edita `src/services/api.js`:
```javascript
const API_BASE_URL = 'http://tu-servidor:puerto/api';
```

### Proxy (Vite)
El archivo `vite.config.js` incluye un proxy para desarrollo:
```javascript
proxy: {
  '/api': {
    target: 'http://localhost:8080',
    changeOrigin: true
  }
}
```

## Tecnologías Utilizadas

- **React 18.2.0**
- **React Router DOM 6.20.0**
- **Axios 1.6.2**
- **Vite 5.0.8**
- **Vitest 1.0.4**
- **React Testing Library 14.1.2**

## Características de Diseño

- Diseño responsive
- Sin frameworks de UI (CSS puro)
- Navegación lateral con menú
- Tablas con búsqueda
- Formularios con validación
- Mensajes de error y éxito
- Indicadores visuales (badges, colores)

## Solución de Problemas

### Error de conexión con el backend
- Verifica que el backend esté ejecutándose en `http://localhost:8080`
- Revisa la configuración de `API_BASE_URL` en `src/services/api.js`
- Verifica la configuración CORS en el backend

### Error al instalar dependencias
- Elimina `node_modules` y `package-lock.json`
- Ejecuta `npm install` nuevamente

### Puerto 3000 en uso
- Cambia el puerto en `vite.config.js`:
  ```javascript
  server: {
    port: 3001
  }
  ```

## Autor

Cristian Chele Tapia
