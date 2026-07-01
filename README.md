# E-commerce API

API REST para gestionar un ecommerce simple: usuarios, autenticacion, productos, categorias, marcas y ventas.

## URLs principales

| Recurso | URL |
|---------|-----|
| Swagger UI | https://ecommerce-demo-ff8r.onrender.com/swagger-ui.html |

Usar Swagger como punto de entrada para explorar y probar la API.

## Tecnologias usadas

| Tecnologia | Uso en el proyecto |
|------------|--------------------|
| Java 21 | Lenguaje principal |
| Spring Boot 3.5 | Framework base de la API |
| Spring Web | Controladores REST |
| Spring Data JPA | Persistencia y repositorios |
| Spring Security | Configuracion de seguridad y autenticacion |
| PostgreSQL | Base de datos principal |
| H2 | Dependencia disponible para entornos de prueba/desarrollo |
| JWT (`jjwt`) | Tokens de autenticacion |
| MapStruct | Mapeo entre DTOs, comandos y modelos |
| Lombok | Reduccion de codigo repetitivo |
| Bean Validation | Validacion de requests |
| Springdoc OpenAPI / Swagger | Documentacion interactiva de endpoints |
| Docker | Empaquetado para despliegue |
| Render | Hosting de la API y PostgreSQL |
| Maven | Gestion de dependencias y build |

## Como usar la API desplegada

La forma mas comoda de explorar la API es desde Swagger:

```txt
https://ecommerce-demo-ff8r.onrender.com/swagger-ui.html
```

Desde ahi se pueden ver los endpoints, parametros, modelos de request/response y probar llamadas HTTP.

### Verificar que la API responde

```bash
curl https://ecommerce-demo-ff8r.onrender.com/api/v1/products/all
```

Una respuesta como esta significa que la aplicacion esta viva y conectada a la base, aunque todavia no tenga productos cargados:

```json
{
  "content": [],
  "totalElements": 0,
  "empty": true
}
```

## Endpoints principales

### Autenticacion

| Metodo | Endpoint | Uso |
|--------|----------|-----|
| `POST` | `/api/v1/auth/login` | Iniciar sesion. Devuelve tokens en cookies HttpOnly. |
| `POST` | `/api/v1/auth/refresh` | Renovar tokens usando un refresh token. |
| `POST` | `/api/v1/auth/logout` | Cerrar sesion. |

### Usuarios

| Metodo | Endpoint | Uso |
|--------|----------|-----|
| `POST` | `/api/v1/users/register` | Registrar un usuario. |
| `GET` | `/api/v1/users/me` | Consultar el usuario autenticado. |

### Productos

| Metodo | Endpoint | Uso |
|--------|----------|-----|
| `POST` | `/api/v1/products` | Crear un producto. |
| `GET` | `/api/v1/products/all` | Listar productos con paginacion y filtros. |
| `GET` | `/api/v1/products/{id}` | Obtener un producto por ID. |
| `PUT` | `/api/v1/products/{id}` | Actualizar un producto. |
| `DELETE` | `/api/v1/products/{id}` | Eliminar un producto. |

Parametros disponibles en `/api/v1/products/all`:

| Parametro | Descripcion | Default |
|-----------|-------------|---------|
| `page` | Pagina a consultar | `0` |
| `size` | Cantidad de elementos por pagina | `10` |
| `sortBy` | Campo por el que se ordena | `id` |
| `sortDirection` | Direccion de ordenamiento (`asc` o `desc`) | `asc` |
| `category` | Filtro por categoria | opcional |
| `brand` | Filtro por marca | opcional |
| `minPrice` | Precio minimo | opcional |
| `maxPrice` | Precio maximo | opcional |
| `active` | Filtro por estado activo/inactivo | opcional |

Ejemplo:

```txt
GET /api/v1/products/all?page=0&size=10&sortBy=price&sortDirection=desc
```

### Categorias

| Metodo | Endpoint | Uso |
|--------|----------|-----|
| `POST` | `/api/v1/categories` | Crear una categoria. |
| `GET` | `/api/v1/categories/all` | Listar categorias. |
| `GET` | `/api/v1/categories/{id}` | Buscar categoria por ID. |
| `GET` | `/api/v1/categories/{name}` | Buscar categoria por nombre. |
| `PUT` | `/api/v1/categories/{id}` | Actualizar una categoria. |
| `DELETE` | `/api/v1/categories/{id}` | Eliminar una categoria. |

Parametros disponibles en `/api/v1/categories/all`:

| Parametro | Descripcion | Default |
|-----------|-------------|---------|
| `page` | Pagina a consultar | `0` |
| `size` | Cantidad de elementos por pagina | `10` |
| `sortBy` | Campo por el que se ordena | `name` |
| `sortDirection` | Direccion de ordenamiento (`asc` o `desc`) | `asc` |

### Marcas

| Metodo | Endpoint | Uso |
|--------|----------|-----|
| `POST` | `/api/v1/brands` | Crear una marca. |
| `GET` | `/api/v1/brands/all` | Listar marcas. |
| `GET` | `/api/v1/brands/{id}` | Buscar marca por ID. |
| `GET` | `/api/v1/brands/{name}` | Buscar marca por nombre. |
| `PUT` | `/api/v1/brands/{id}` | Actualizar una marca. |
| `DELETE` | `/api/v1/brands/{id}` | Eliminar una marca. |

Parametros disponibles en `/api/v1/brands/all`:

| Parametro | Descripcion | Default |
|-----------|-------------|---------|
| `page` | Pagina a consultar | `0` |
| `size` | Cantidad de elementos por pagina | `10` |
| `sortBy` | Campo por el que se ordena | `name` |
| `sortDirection` | Direccion de ordenamiento (`asc` o `desc`) | `asc` |

### Ventas

| Metodo | Endpoint | Uso |
|--------|----------|-----|
| `POST` | `/api/v1/sales` | Crear una venta. |
| `GET` | `/api/v1/sales` | Consultar ventas con paginacion/filtros. |
| `GET` | `/api/v1/sales/{id}` | Obtener una venta por ID. |

Parametros disponibles en `/api/v1/sales`:

| Parametro | Descripcion | Default |
|-----------|-------------|---------|
| `startDate` | Fecha inicial (`YYYY-MM-DD`) | hoy |
| `endDate` | Fecha final (`YYYY-MM-DD`) | hoy |
| `page` | Pagina a consultar | `0` |
| `size` | Cantidad de elementos por pagina | `10` |
| `sortBy` | Campo por el que se ordena | `date` |
| `sortDirection` | Direccion de ordenamiento (`asc` o `desc`) | `desc` |

## Ejecutar localmente

### Requisitos

- Java 21+
- Docker
- Maven Wrapper incluido en el proyecto

### 1. Levantar PostgreSQL

```bash
docker compose up -d
```

Configuracion local definida en `docker-compose.yml` y `application-dev.properties`:

| Dato | Valor |
|------|-------|
| Host | `localhost` |
| Puerto | `5433` |
| Base | `DB_ecommerce` |
| Usuario | `user` |
| Password | `123456` |

### 2. Iniciar la aplicacion

```bash
# Linux/Mac
./mvnw spring-boot:run

# Windows
mvnw.cmd spring-boot:run
```

La API local queda disponible en:

```txt
http://localhost:8080
```

Base path versionado de la API:

```txt
http://localhost:8080/api/v1
```

Swagger local:

```txt
http://localhost:8080/swagger-ui.html
```

## Build

```bash
# Linux/Mac
./mvnw clean package

# Windows
mvnw.cmd clean package
```

El `.jar` queda generado en `target/`.

## Despliegue en Render

El despliegue esta preparado con Docker:

| Archivo | Uso |
|---------|-----|
| `Dockerfile` | Construye y ejecuta la aplicacion con Java 21. |
| `render.yaml` | Define el Web Service y la base PostgreSQL para Render. |
| `src/main/resources/application-prod.properties` | Configuracion del perfil `prod`. |

Variables principales usadas en Render:

| Variable | Uso |
|----------|-----|
| `SPRING_PROFILES_ACTIVE=prod` | Activa configuracion de produccion. |
| `PORT` | Puerto inyectado por Render. |
| `RENDER_DATABASE_HOST` | Host de PostgreSQL en Render. |
| `RENDER_DATABASE_PORT` | Puerto de PostgreSQL. |
| `RENDER_DATABASE_NAME` | Nombre de la base. |
| `RENDER_DATABASE_USER` | Usuario de la base. |
| `RENDER_DATABASE_PASSWORD` | Password de la base. |
| `JWT_SECRET` | Secreto para firmar tokens JWT. |

## Estructura del proyecto

```txt
src/main/java/com/demo/ecommerce
- domain                         # Modelos y reglas de dominio
- application                    # Casos de uso y puertos
- infrastructure
  - input/web                    # Controladores REST, DTOs y mappers
  - output/persistence           # Persistencia JPA
  - config                       # Configuracion de la aplicacion
  - exception                    # Manejo de errores
  - security                     # JWT, cookies y seguridad
```

## Notas importantes

- La API no tiene una pagina web frontend; se consume como API REST.
- La documentacion interactiva esta en Swagger UI.
