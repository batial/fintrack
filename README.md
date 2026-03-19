# FinTrack API

API REST de gestión de finanzas personales construida con Spring Boot.

##  Descripción

FinTrack permite a los usuarios registrar y analizar sus movimientos financieros. Cada usuario puede gestionar sus ingresos y gastos, organizarlos por categorías, definir presupuestos mensuales y consultar reportes de sus finanzas.

##  Tecnologías

- **Java 21**
- **Spring Boot 4**
- **Spring Security + JWT**
- **PostgreSQL** — base de datos relacional
- **Redis** — caché de reportes
- **Docker** — contenedores
- **Swagger / OpenAPI** — documentación interactiva

##  Requisitos

- Java 21
- Docker Desktop

##  Cómo correr el proyecto

**1. Clonar el repositorio**
```bash
git clone https://github.com/batial/fintrack.git
cd fintrack
```

**2. Levantar la base de datos y Redis**
```bash
docker compose up -d
```

**3. Correr la aplicación**
```bash
./mvnw spring-boot:run
```

**4. Acceder a la documentación**
```
http://localhost:8080/swagger-ui/index.html
```

##  Autenticación

La API usa JWT. Para usar los endpoints protegidos:

1. Registrarse en `POST /api/auth/register`
2. Hacer login en `POST /api/auth/login`
3. Usar el token recibido en el header `Authorization: Bearer <token>`

##  Endpoints principales

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| POST | /api/auth/register | Registrar usuario |
| POST | /api/auth/login | Login |
| GET | /api/categories | Listar categorías |
| POST | /api/categories | Crear categoría |
| GET | /api/transactions | Listar transacciones |
| POST | /api/transactions | Crear transacción |
| GET | /api/budgets | Listar presupuestos |
| POST | /api/budgets | Crear presupuesto |
| GET | /api/reports/summary | Reporte mensual |

##  Modelo de datos
```
users
 ├── categories
 ├── transactions
 └── budgets
```