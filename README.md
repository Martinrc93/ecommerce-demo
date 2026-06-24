# E-commerce API

API RESTful para la gestión de un sistema de comercio electrónico, desarrollada con Spring Boot 3 y Java 21. Incluye gestión de productos, ventas, marcas, categorías y autenticación con JWT.

## Estado del despliegue

La API está desplegada en Render:

- URL pública: https://ecommerce-demo-ff8r.onrender.com
- Endpoint de verificación funcional: https://ecommerce-demo-ff8r.onrender.com/products/all

La ruta raíz `/` no expone una página o endpoint de bienvenida. Si se abre la URL base directamente, la aplicación puede devolver un error JSON porque no hay un controlador definido para `/`.

## Requisitos previos

- Java 21 o superior
- Maven 3.8+ o Maven Wrapper incluido en el repositorio
- PostgreSQL para desarrollo local y producción
- Docker, solo si se quiere reproducir el despliegue de Render localmente

## Tecnologías principales

- Spring Boot 3.x
- Spring Security
- Spring Data JPA
- PostgreSQL
- MapStruct
- JWT
- Swagger / OpenAPI 3
- Docker
- Render

## Configuración y perfiles

La aplicación usa perfiles de Spring Boot:

| Perfil | Archivo | Uso |
|--------|---------|-----|
| `dev` | `src/main/resources/application-dev.properties` | Desarrollo local |
| `prod` | `src/main/resources/application-prod.properties` | Render / producción |

Por defecto, `src/main/resources/application.properties` activa el perfil `dev`.

## Desarrollo local

El perfil `dev` usa PostgreSQL local en el puerto `5433`, alineado con `docker-compose.yml`.

### 1. Levantar PostgreSQL local

```bash
docker compose up -d
```

La base local queda configurada con:

```txt
Host: localhost
Port: 5433
Database: DB_ecommerce
User: user
Password: 123456
```

### 2. Ejecutar la aplicación

```bash
# Linux/Mac
./mvnw spring-boot:run

# Windows
mvnw.cmd spring-boot:run
```

Swagger local está disponible en:

```txt
http://localhost:8080/swagger-ui.html
```

## Producción en Render

El despliegue usa Docker y está definido por:

- `Dockerfile`
- `render.yaml`
- `src/main/resources/application-prod.properties`

### Servicio web

En Render, la aplicación debe correr como **Web Service** con runtime **Docker**.

Render inyecta el puerto mediante la variable `PORT`, y la aplicación lo toma con:

```properties
server.port=${PORT:10000}
```

### Variables de entorno requeridas

Si se usa `render.yaml` como Blueprint, Render puede crear la base Postgres e inyectar estas variables automáticamente.

Si se configura manualmente, cargar estas variables en el Web Service:

| Variable | Descripción |
|----------|-------------|
| `SPRING_PROFILES_ACTIVE` | Debe ser `prod` |
| `RENDER_DATABASE_HOST` | Host real de Postgres en Render |
| `RENDER_DATABASE_PORT` | Puerto de Postgres, normalmente `5432` |
| `RENDER_DATABASE_NAME` | Nombre real de la base |
| `RENDER_DATABASE_USER` | Usuario real de la base |
| `RENDER_DATABASE_PASSWORD` | Password real de la base |
| `JWT_SECRET` | Secreto para firmar tokens JWT |

Variables opcionales:

| Variable | Valor por defecto | Descripción |
|----------|-------------------|-------------|
| `JWT_ACCESS_TOKEN_EXPIRATION` | `900000` | Duración del access token en milisegundos |
| `JWT_REFRESH_TOKEN_EXPIRATION` | `1800000` | Duración del refresh token en milisegundos |
| `COOKIE_DOMAIN` | vacío | Dominio de cookies si se usa un dominio propio |

No usar placeholders como `<host postgres render>` como valor real. Deben reemplazarse por los datos que Render muestra en la sección de conexión de la base Postgres.

### Swagger en producción

Swagger está deshabilitado por defecto en `prod` por seguridad:

```properties
springdoc.swagger-ui.enabled=false
springdoc.api-docs.enabled=false
```

Para habilitarlo temporalmente en Render, agregar estas variables al Web Service:

```env
SPRINGDOC_SWAGGER_UI_ENABLED=true
SPRINGDOC_API_DOCS_ENABLED=true
```

Luego ejecutar un redeploy. La URL será:

```txt
https://ecommerce-demo-ff8r.onrender.com/swagger-ui.html
```

Deshabilitarlo nuevamente cuando no sea necesario exponer la documentación pública.

## Endpoints principales

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| `POST` | `/auth/login` | Login de usuarios |
| `POST` | `/auth/refresh` | Renovación de token |
| `POST` | `/auth/logout` | Logout |
| `POST` | `/users/register` | Registro de usuarios |
| `GET` | `/users/me` | Usuario autenticado |
| `GET` | `/products/all` | Listado paginado de productos |
| `GET` | `/products/{id}` | Producto por ID |
| `GET` | `/categories/all` | Listado paginado de categorías |
| `GET` | `/brands/all` | Listado paginado de marcas |
| `GET` | `/sales` | Consulta paginada de ventas |

## Notas de operación

- Una respuesta vacía en `/products/all` significa que la aplicación está funcionando, pero la base no tiene productos cargados.
- El archivo `data.sql` no se ejecuta automáticamente en producción salvo que se configure explícitamente la inicialización SQL de Spring.
- Actualmente la configuración de seguridad debe revisarse antes de considerar el servicio listo para producción real.

## Estructura del proyecto

El proyecto sigue una arquitectura en capas adaptada a principios de arquitectura limpia/hexagonal:

- `domain`: modelos y excepciones de dominio.
- `application`: casos de uso y puertos.
- `infrastructure/input/web`: controladores REST y DTOs.
- `infrastructure/output/persistence`: entidades JPA, repositorios y adaptadores de persistencia.
- `infrastructure/config`: configuración de seguridad, persistencia y documentación.
