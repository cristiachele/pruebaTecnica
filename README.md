# Proyecto Bancario

Sistema bancario completo desarrollado con Spring Boot (Backend) y React (Frontend), siguiendo arquitectura hexagonal y mejores prácticas de desarrollo.

## URL GIT

```
 https://github.com/cristiachele/pruebaTecnica.git
```

## Estructura del Proyecto

```
.
├── ProyectoBancario/      # Backend (Spring Boot)
│   ├── src/
│   ├── docker-compose.yml
│   ├── Dockerfile
│   ├── BaseDatos.sql
│   ├── Postman_Collection.json
│   ├── CURL_Commands.md
│   └── README.md
│
└── Proyectofrom/          # Frontend (React)
    ├── src/
    ├── Dockerfile
    ├── nginx.conf
    └── README.md
```

## Requisitos Previos

### Para Backend
- Java 17 o superior
- Maven 3.6 o superior
- Docker y Docker Compose (recomendado)
- PostgreSQL 15 (si no usas Docker)

### Para Frontend
- Node.js 18 o superior
- npm o yarn
- Docker (opcional)

## Instalación y Ejecución Rápida

### 1. Backend

#### Con Docker (Recomendado)
```bash
cd ProyectoBancario
docker-compose up --build
```

El backend estará disponible en `http://localhost:8080`

#### Sin Docker
```bash
cd ProyectoBancario
# Configura PostgreSQL y ejecuta BaseDatos.sql
mvn clean install
mvn spring-boot:run
```

### 2. Frontend

#### Ejecución Local
```bash
cd Proyectofrom
npm install
npm run dev
```

El frontend estará disponible en `http://localhost:3000`

#### Con Docker
```bash
cd Proyectofrom
docker build -t banco-frontend .
docker run -p 80:80 banco-frontend
```

## Instrucciones Detalladas

### Backend

1. **Navega al directorio:**
   ```bash
   cd C:\Users\crist\Documents\Cursor\ProyectoBancario
   ```

2. **Con Docker:**
   - Asegúrate de tener Docker Desktop ejecutándose
   - Ejecuta: `docker-compose up --build`
   - Espera a que los contenedores estén listos

3. **Sin Docker:**
   - Instala PostgreSQL
   - Crea la base de datos `bancodb`
   - Ejecuta el script `BaseDatos.sql`
   - Configura `application.yml` con tus credenciales
   - Ejecuta: `mvn spring-boot:run`

4. **Verifica que funcione:**
   ```bash
   curl http://localhost:8080/api/clientes
   ```

### Frontend

1. **Navega al directorio:**
   ```bash
   cd C:\Users\crist\Documents\Cursor\Proyectofrom
   ```

2. **Instala dependencias:**
   ```bash
   npm install
   ```

3. **Inicia el servidor de desarrollo:**
   ```bash
   npm run dev
   ```

4. **Abre el navegador:**
   - Ve a `http://localhost:3000`
   - Asegúrate de que el backend esté ejecutándose

## Pruebas

### Backend

#### Pruebas Unitarias
```bash
cd ProyectoBancario
mvn test
```

#### Pruebas con Postman
1. Importa `Postman_Collection.json` en Postman
2. Ejecuta las peticiones de la colección

#### Pruebas con CURL
Consulta `CURL_Commands.md` para ejemplos

### Frontend

#### Pruebas Unitarias
```bash
cd Proyectofrom
npm test
```

## Endpoints Principales

### Backend (Base: `http://localhost:8080/api`)

- **Clientes:** `/clientes`
- **Cuentas:** `/cuentas`
- **Movimientos:** `/movimientos`
- **Reportes:** `/reportes`

Ver `ProyectoBancario/README.md` para documentación completa.

## Características Principales

### Backend
- ✅ Arquitectura hexagonal
- ✅ Patrón Repository
- ✅ JPA / Entity Framework Core
- ✅ Manejo de excepciones
- ✅ Pruebas unitarias (mínimo 2)
- ✅ Docker configurado
- ✅ Generación de reportes (JSON y PDF base64)
- ✅ Validaciones de negocio

### Frontend
- ✅ React con React Testing Library
- ✅ Sin frameworks de UI (CSS puro)
- ✅ CRUD completo para todas las entidades
- ✅ Búsqueda rápida
- ✅ Validación de formularios
- ✅ Mensajes de error y éxito
- ✅ Generación y descarga de reportes PDF

## Reglas de Negocio Implementadas

1. **Movimientos:**
   - Créditos: valores positivos
   - Débitos: valores negativos
   - Validación de saldo disponible
   - Límite diario de retiro: $1000.00
   - Mensaje "Saldo no disponible" cuando el saldo es cero
   - Mensaje "Cupo diario Excedido" cuando se supera el límite

2. **Reportes:**
   - Generación en formato JSON
   - Generación en formato PDF (base64)
   - Filtrado por cliente y rango de fechas
   - Total de créditos y débitos

## Tecnologías

### Backend
- Spring Boot 3.2.0
- Spring Data JPA
- PostgreSQL
- Lombok
- MapStruct
- iText7
- JUnit 5 / Mockito

### Frontend
- React 18.2.0
- React Router DOM
- Axios
- Vite
- Vitest
- React Testing Library

## Solución de Problemas

### Backend no inicia
- Verifica que PostgreSQL esté ejecutándose
- Revisa las credenciales en `application.yml`
- Verifica que el puerto 8080 esté disponible

### Frontend no se conecta al backend
- Verifica que el backend esté ejecutándose
- Revisa `API_BASE_URL` en `src/services/api.js`
- Verifica la configuración CORS

### Error de compilación
- Verifica las versiones de Java (17+) y Node.js (18+)
- Limpia y recompila: `mvn clean install` o `npm install`

## Documentación Adicional

- **Backend:** Ver `ProyectoBancario/README.md`
- **Frontend:** Ver `Proyectofrom/README.md`
- **CURL Commands:** Ver `ProyectoBancario/CURL_Commands.md`
- **Postman Collection:** `ProyectoBancario/Postman_Collection.json`

## Autor

Sistema Bancario - Proyecto de Evaluación Técnica
