# Evaluación Plataformas Especiales 2026

Aplicación para procesar transacciones utilizando dos APIs Spring Boot y un frontend desarrollado con React.

## Tecnologías

### Backend
- Java 17
- Spring Boot
- Spring Data JPA
- H2
- BCrypt
- AES-256
- RestTemplate
- JUnit
- Mockito

### Frontend
- React
- TypeScript
- Vite

---

## Arquitectura

```text
React :5173
    |
    v
API Operaciones :8080
    |
    | RestTemplate
    v
API Transacciones :8081
    |
    | JPA
    v
H2
```

### API Operaciones

Responsable de:

- Recibir las solicitudes del frontend.
- Validar los datos con `@Valid` y `@Pattern`.
- Manejar errores con `@RestControllerAdvice`.
- Descifrar el secreto utilizando AES.
- Enviar la transacción a API Transacciones mediante `RestTemplate`.

### API Transacciones

Responsable de:

- Guardar las transacciones con Spring Data JPA.
- Utilizar H2 como base de datos.
- Generar referencias de 6 dígitos.
- Asignar estatus `Aprobada`.
- Validar usuarios con BCrypt.
- Cancelar transacciones mediante PATCH y `@Query`.
- Consultar transacciones con paginación.

---

# Ejecución

## 1. API Transacciones

Ejecutar:

```bash
cd api-transacciones
mvn spring-boot:run
```

Disponible en:

```text
http://localhost:8081
```

---

## 2. API Operaciones

Ejecutar:

```bash
cd api-operaciones
mvn spring-boot:run
```

Disponible en:

```text
http://localhost:8080
```

---

## 3. Frontend

Ejecutar:

```bash
cd frontend
npm install
npm run dev
```

Disponible en:

```text
http://localhost:5173
```

---

# Usuario de prueba

```text
Usuario: admin
Password: 123456
```

El password se almacena utilizando BCrypt.

---

# Registrar operación

Ejemplo:

```text
Operación: venta
Importe: 100.00
Cliente: Angel
Secreto: pikachu
```

El frontend cifra el secreto con AES antes de enviarlo.

Endpoint:

```http
POST http://localhost:8080/api/operaciones
```

Ejemplo de respuesta:

```json
{
  "id": 1,
  "estatus": "Aprobada",
  "referencia": "262737",
  "operacion": "venta"
}
```

---

# Cancelar operación

Endpoint:

```http
PATCH http://localhost:8081/api/transacciones
```

Request:

```json
{
  "id": 1,
  "referencia": "262737",
  "estatus": "cancelar"
}
```

La operación cambia de:

```text
Aprobada
```

a:

```text
Cancelada
```

---

# Paginación

Ejemplo:

```http
GET http://localhost:8081/api/transacciones?pagina=0&cantidad=10&ordenarPor=id
```

---

# H2

Consola:

```text
http://localhost:8081/h2-console
```

Datos de conexión:

```text
JDBC URL: jdbc:h2:mem:evaluaciondb
Usuario: sa
Password: vacío
```

Consultas:

```sql
SELECT * FROM TRANSACCIONES;
```

```sql
SELECT * FROM USUARIOS;
```

---

# Testing

Se utilizan:

- JUnit
- Mockito
- MockMvc

Se probaron escenarios como:

- Registro de transacciones.
- Cancelación.
- Paginación.
- Login correcto e incorrecto.
- BCrypt.
- AES.
- Validaciones de los campos.

Ejecutar pruebas:

```bash
mvn test
```