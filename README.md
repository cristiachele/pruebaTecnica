# Guía de Docker - Proyecto Bancario Completo

Este documento explica cómo ejecutar tanto el **Backend (ProyectoBancario)** como el **Frontend (Proyectofrom)** usando Docker con un único `docker-compose.yml`.

## Requisitos Previos

- Java 21 o superior
- Maven 3.9.12 o superior
- Docker y Docker Compose (para ejecución con contenedores)
- Node 11.6.4
- PostgreSQL 15 (si se ejecuta sin Docker)

## 📋 Estructura del Proyecto

```
pruebaTecnica/
├── docker-compose.yml          # ← Archivo principal (en la raíz)
├── ProyectoBancario/          # Backend Spring Boot
│   ├── Dockerfile
│   ├── pom.xml
│   └── src/
└── Proyectofrom/              # Frontend React
    ├── Dockerfile
    ├── package.json
    └── src/
```

## 🚀 Inicio Rápido

### 1. Ejecutar Todo el Stack

Desde la raíz del proyecto (`C:\Users\crist\Documents\pruebaTecnica`):

```bash
docker-compose up -d
```

Este comando:
- ✅ Construye las imágenes del backend y frontend
- ✅ Inicia PostgreSQL
- ✅ Ejecuta el script SQL de inicialización
- ✅ Inicia el backend Spring Boot
- ✅ Inicia el frontend con Nginx

### 2. Acceder a la Aplicación

Una vez que todos los contenedores estén ejecutando:

- **Frontend:** http://localhost
- **Backend API:** http://localhost:8080/api/clientes
- **PostgreSQL:** localhost:5432

### 3. Ver Logs

```bash
# Ver logs de todos los servicios
docker-compose logs -f

# Ver logs de un servicio específico
docker-compose logs -f backend
docker-compose logs -f frontend
docker-compose logs -f postgres
```

### 4. Detener los Servicios

```bash
# Detener y eliminar contenedores
docker-compose down

# Detener y eliminar contenedores + volúmenes (⚠️ elimina datos de BD)
docker-compose down -v
```

## 🔧 Servicios Incluidos

### 1. PostgreSQL (Base de Datos)
- **Puerto:** 5432
- **Base de datos:** bancodb
- **Usuario:** postgres
- **Contraseña:** postgres
- **Volumen:** `postgres_data` (persistencia de datos)

### 2. Backend (ProyectoBancario)
- **Puerto:** 8080
- **Contexto de build:** `./ProyectoBancario`
- **Dockerfile:** `ProyectoBancario/Dockerfile`
- **Depende de:** PostgreSQL (espera a que esté saludable)

### 3. Frontend (Proyectofrom)
- **Puerto:** 80
- **Contexto de build:** `./Proyectofrom`
- **Dockerfile:** `Proyectofrom/Dockerfile`
- **Depende de:** Backend (espera a que esté saludable)
- **Proxy:** Las peticiones a `/api` se redirigen al backend
