# Comandos CURL para Pruebas del Backend

## Clientes

### Crear Cliente
```bash
curl -X POST http://localhost:8080/api/clientes \
  -H "Content-Type: application/json" \
  -d '{
    "nombre": "Jose Lema",
    "genero": "Masculino",
    "edad": 35,
    "identificacion": "1234567890",
    "direccion": "Otavalo sn y principal",
    "telefono": "098254785",
    "clienteId": "CLI001",
    "contrasena": "1234",
    "estado": true
  }'
```

### Obtener Todos los Clientes
```bash
curl -X GET http://localhost:8080/api/clientes
```

### Obtener Cliente por ID
```bash
curl -X GET http://localhost:8080/api/clientes/1
```

### Actualizar Cliente
```bash
curl -X PUT http://localhost:8080/api/clientes/1 \
  -H "Content-Type: application/json" \
  -d '{
    "nombre": "Jose Lema Actualizado",
    "genero": "Masculino",
    "edad": 36,
    "identificacion": "1234567890",
    "direccion": "Otavalo sn y principal",
    "telefono": "098254785",
    "clienteId": "CLI001",
    "contrasena": "1234",
    "estado": true
  }'
```

### Eliminar Cliente
```bash
curl -X DELETE http://localhost:8080/api/clientes/1
```

## Cuentas

### Crear Cuenta
```bash
curl -X POST http://localhost:8080/api/cuentas \
  -H "Content-Type: application/json" \
  -d '{
    "numeroCuenta": "478758",
    "tipoCuenta": "Ahorro",
    "saldoInicial": 2000.00,
    "estado": true,
    "clienteId": 1
  }'
```

### Obtener Todas las Cuentas
```bash
curl -X GET http://localhost:8080/api/cuentas
```

### Obtener Cuenta por ID
```bash
curl -X GET http://localhost:8080/api/cuentas/1
```

### Obtener Cuentas por Cliente
```bash
curl -X GET http://localhost:8080/api/cuentas/cliente/1
```

### Actualizar Cuenta
```bash
curl -X PUT http://localhost:8080/api/cuentas/1 \
  -H "Content-Type: application/json" \
  -d '{
    "numeroCuenta": "478758",
    "tipoCuenta": "Ahorro",
    "saldoInicial": 2500.00,
    "estado": true,
    "clienteId": 1
  }'
```

### Eliminar Cuenta
```bash
curl -X DELETE http://localhost:8080/api/cuentas/1
```

## Movimientos

### Crear Movimiento Crédito
```bash
curl -X POST http://localhost:8080/api/movimientos \
  -H "Content-Type: application/json" \
  -d '{
    "tipoMovimiento": "Crédito",
    "valor": 600.00,
    "cuentaId": 1
  }'
```

### Crear Movimiento Débito
```bash
curl -X POST http://localhost:8080/api/movimientos \
  -H "Content-Type: application/json" \
  -d '{
    "tipoMovimiento": "Débito",
    "valor": 575.00,
    "cuentaId": 1
  }'
```

### Obtener Todos los Movimientos
```bash
curl -X GET http://localhost:8080/api/movimientos
```

### Obtener Movimientos por Cuenta
```bash
curl -X GET http://localhost:8080/api/movimientos/cuenta/1
```

### Eliminar Movimiento
```bash
curl -X DELETE http://localhost:8080/api/movimientos/1
```

## Reportes

### Generar Reporte JSON
```bash
curl -X GET "http://localhost:8080/api/reportes?clienteId=1&fechaInicio=2022-02-01T00:00:00&fechaFin=2022-02-28T23:59:59"
```

### Generar Reporte PDF (Base64)
```bash
curl -X GET "http://localhost:8080/api/reportes/pdf?clienteId=1&fechaInicio=2022-02-01T00:00:00&fechaFin=2022-02-28T23:59:59"
```
