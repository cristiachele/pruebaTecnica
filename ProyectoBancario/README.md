# Proyecto Bancario - Backend

Sistema bancario desarrollado con Spring Boot, Maven y arquitectura hexagonal.

## Requisitos Previos

- Java 21 o superior
- Maven 3.9.12 o superior
- Docker y Docker Compose (para ejecución con contenedores)
- PostgreSQL 15 (si se ejecuta sin Docker)

## Estructura del Proyecto

El proyecto sigue una arquitectura hexagonal (puertos y adaptadores):

```
src/
├── main/
│   ├── java/
│   │   └── ec/com/bamco/proyectobancario/
│   │       ├── domain/              # Capa de dominio
│   │       │   ├── model/           # Entidades del dominio
│   │       │   └── port/            # Puertos (interfaces)
│   │       ├── application/         # Capa de aplicación
│   │       │   └── usecase/         # Casos de uso
│   │       ├── infrastructure/      # Capa de infraestructura
│   │       │   ├── persistence/     # JPA, Repositorios
│   │       │   └── service/         # Servicios externos
│   │       └── adapter/             # Adaptadores
│   │           └── input/web/        # Controladores REST
│   └── resources/
│       ├── application.yml          # Configuración
│       └── BaseDatos.sql            # Script SQL
└── test/                             # Pruebas unitarias
```

## Instalación y Ejecución

### Opción 1: Ejecución con Docker (Recomendado)

1. **Asegúrate de tener Docker y Docker Compose instalados:**
   ```bash
   docker --version
   docker-compose --version
   ```

2. **Navega al directorio del proyecto:**
   ```bash
   cd ProyectoBancario
   ```

3. **Construye y ejecuta los contenedores:**
   ```bash
   docker-compose up --build
   ```

   Este comando:
   - Construye la imagen del backend
   - Inicia PostgreSQL
   - Ejecuta el script SQL de inicialización
   - Inicia el backend en el puerto 8080

4. **Verifica que el servicio esté funcionando:**
   ```bash
   curl http://localhost:8080/api/clientes
   ```

### Opción 2: Ejecución Local (Sin Docker)

1. **Instala PostgreSQL y crea la base de datos:**
   ```bash
   # En PostgreSQL
   CREATE DATABASE bancodb;
   CREATE USER postgres WITH PASSWORD 'postgres';
   GRANT ALL PRIVILEGES ON DATABASE bancodb TO postgres;
   ```

2. **Ejecuta el script SQL:**
   ```bash
   psql -U postgres -d bancodb -f src/main/resources/BaseDatos.sql
   ```

3. **Configura las variables de entorno (opcional):**
   - Edita `src/main/resources/application.yml` si necesitas cambiar la configuración

4. **Compila el proyecto:**
   ```bash
   mvn clean install
   ```

5. **Ejecuta la aplicación:**
   ```bash
   mvn spring-boot:run
   ```

   O ejecuta el JAR:
   ```bash
   java -jar target/proyecto-bancario-1.0.0.jar
   ```

## Endpoints de la API

### Base URL
```
http://localhost:8080/api
```

### Clientes
- `GET /clientes` - Obtener todos los clientes
- `GET /clientes/{id}` - Obtener cliente por ID
- `POST /clientes` - Crear cliente
- `PUT /clientes/{id}` - Actualizar cliente
- `DELETE /clientes/{id}` - Eliminar cliente

### Cuentas
- `GET /cuentas` - Obtener todas las cuentas
- `GET /cuentas/{id}` - Obtener cuenta por ID
- `GET /cuentas/cliente/{clienteId}` - Obtener cuentas por cliente
- `POST /cuentas` - Crear cuenta
- `PUT /cuentas/{id}` - Actualizar cuenta
- `DELETE /cuentas/{id}` - Eliminar cuenta

### Movimientos
- `GET /movimientos` - Obtener todos los movimientos
- `GET /movimientos/{id}` - Obtener movimiento por ID
- `GET /movimientos/cuenta/{cuentaId}` - Obtener movimientos por cuenta
- `POST /movimientos` - Crear movimiento
- `DELETE /movimientos/{id}` - Eliminar movimiento

### Reportes
- `GET /reportes?clienteId={id}&fechaInicio={fecha}&fechaFin={fecha}` - Generar reporte JSON
- `GET /reportes/pdf?clienteId={id}&fechaInicio={fecha}&fechaFin={fecha}` - Generar reporte PDF (base64)

## Pruebas

### Ejecutar Pruebas Unitarias
```bash
mvn test
```

### Pruebas con Postman
1. Importa la colección `Postman_Collection.json` en Postman
2. Asegúrate de que el backend esté ejecutándose
3. Ejecuta las peticiones de la colección

### Pruebas con CURL
Consulta el archivo `CURL_Commands.md` para ver ejemplos de comandos CURL.

## Configuración

### application.yml
```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/bancodb
    username: postgres
    password: postgres
  
  jpa:
    hibernate:
      ddl-auto: validate

app:
  daily-withdrawal-limit: 1000.00  # Límite diario de retiro
```

## Reglas de Negocio

1. **Movimientos:**
   - Los créditos son valores positivos
   - Los débitos son valores negativos
   - Se valida el saldo disponible antes de realizar débitos
   - Límite diario de retiro: $1000.00

2. **Validaciones:**
   - No se puede realizar un débito si el saldo es cero o negativo
   - No se puede exceder el límite diario de retiro
   - Los clienteId y números de cuenta deben ser únicos

## Tecnologías Utilizadas

- **Spring Boot 4.0.2**
- **Spring Data JPA**
- **PostgreSQL**
- **Lombok**
- **MapStruct**
- **iText7** (para generación de PDFs)
- **JUnit 5** y **Mockito** (para pruebas)

## Buenas Prácticas Implementadas

- Arquitectura hexagonal (puertos y adaptadores)
- Separación de responsabilidades
- Inyección de dependencias
- Validación de datos
- Manejo de excepciones
- Pruebas unitarias
- Documentación JavaDoc

## Solución de Problemas

### Error de conexión a la base de datos
- Verifica que PostgreSQL esté ejecutándose
- Revisa las credenciales en `application.yml`
- Asegúrate de que la base de datos `bancodb` exista

### Error al compilar
- Verifica que tengas Java 21 instalado: `java -version`
- Limpia y recompila: `mvn clean install`

### Puerto 8080 en uso
- Cambia el puerto en `application.yml`: `server.port: 8081`

## Autor

Cristian Chele Tapia
