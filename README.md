# bookstore-api

API REST para gestión de una librería en línea, construida con Spring Boot.

## Tecnologías

- Java 17
- Spring Boot 3.2
- Spring Security + JWT
- Spring Data JPA
- H2 (desarrollo) / PostgreSQL (producción)
- Maven
- Springdoc OpenAPI (Swagger)

## Requisitos previos

- Java 17+
- Maven 3.8+
- Git 2.x

## Configuración y ejecución local

### 1. Clonar el repositorio

```bash
git clone https://github.com/OmaarAGR/bookstore-api.git
cd bookstore-api
```

### 2. Variables de entorno

El proyecto usa H2 en memoria por defecto para desarrollo. Solo necesitas definir la variable `JWT_SECRET` si quieres un secret personalizado:

```bash
export JWT_SECRET=miClaveSecretaSuperSegura1234567890
```

Si no se define, se usará el valor por defecto del `application.yml`.

### 3. Compilar y ejecutar

```bash
mvn clean install
mvn spring-boot:run
```

La API quedará disponible en: `http://localhost:8080/api/v1`

### 4. Consola H2 (desarrollo)

Disponible en: `http://localhost:8080/api/v1/h2-console`

- JDBC URL: `jdbc:h2:mem:bookstoredb`
- Username: `sa`
- Password: *(vacío)*

### 5. Documentación Swagger

Disponible en: `http://localhost:8080/api/v1/swagger-ui/index.html`

## Endpoints principales

### Autenticación (público)

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| POST | `/auth/register` | Registrar nuevo usuario |
| POST | `/auth/login` | Iniciar sesión y obtener token JWT |

### Libros (GET público, escritura solo ADMIN)

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/books` | Listar libros (paginado, filtros por `authorId` y `categoryId`) |
| GET | `/books/{id}` | Obtener libro por ID |
| POST | `/books` | Crear libro (ADMIN) |
| PUT | `/books/{id}` | Actualizar libro (ADMIN) |
| DELETE | `/books/{id}` | Eliminar libro (ADMIN) |

### Autores

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/authors` | Listar autores |
| GET | `/authors/{id}` | Obtener autor por ID |
| GET | `/authors/{id}/books` | Libros de un autor |
| POST | `/authors` | Crear autor |
| PUT | `/authors/{id}` | Actualizar autor |
| DELETE | `/authors/{id}` | Eliminar autor (ADMIN, falla si tiene libros) |

### Categorías

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/categories` | Listar categorías |
| GET | `/categories/{id}` | Obtener categoría por ID |
| GET | `/categories/{id}/books` | Libros de una categoría |
| POST | `/categories` | Crear categoría |
| PUT | `/categories/{id}` | Actualizar categoría |
| DELETE | `/categories/{id}` | Eliminar categoría |

### Pedidos (requiere autenticación)

| Método | Endpoint | Acceso | Descripción |
|--------|----------|--------|-------------|
| POST | `/orders` | USER / ADMIN | Crear pedido |
| GET | `/orders/my` | USER / ADMIN | Ver mis pedidos |
| GET | `/orders` | Solo ADMIN | Ver todos los pedidos |

## Uso del token JWT

Después de hacer login, incluir el token en el header de cada request protegido:

```
Authorization: Bearer <token>
```

## Estructura de respuestas

**Éxito:**
```json
{
  "status": "success",
  "code": 200,
  "message": "Operación completada",
  "data": { ... },
  "timestamp": "2024-01-15T10:30:00Z"
}
```

**Error:**
```json
{
  "status": "error",
  "code": 404,
  "message": "El recurso solicitado no fue encontrado",
  "errors": ["Book with id 99 not found"],
  "timestamp": "2024-01-15T10:30:00Z",
  "path": "/api/v1/books/99"
}
```

## Flujo Git

```
main
└── develop
    ├── feature/auth-module
    ├── feature/book-catalog
    ├── feature/order-management
    └── feature/author-category
```

- Nunca hacer push directo a `main` o `develop`.
- Todo PR requiere al menos un revisor diferente al autor.
- Eliminar la rama de feature tras el merge.

## Convención de commits

```
feat: add JWT authentication filter
fix: correct price validation in BookRequest
refactor: extract order total calculation to service
docs: add endpoint documentation in AuthController
test: add unit tests for OrderMapper
chore: update application.yml with JWT config
```
