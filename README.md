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
curl https://ecommerce-demo-ff8r.onrender.com/products/all
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
| `POST` | `/auth/login` | Iniciar sesion. Devuelve tokens en cookies HttpOnly. |
| `POST` | `/auth/refresh` | Renovar tokens usando un refresh token. |
| `POST` | `/auth/logout` | Cerrar sesion. |

### Usuarios

| Metodo | Endpoint | Uso |
|--------|----------|-----|
| `POST` | `/users/register` | Registrar un usuario. |
| `GET` | `/users/me` | Consultar el usuario autenticado. |

### Productos

| Metodo | Endpoint | Uso |
|--------|----------|-----|
| `POST` | `/products` | Crear un producto. |
| `GET` | `/products/all` | Listar productos con paginacion y filtros. |
| `GET` | `/products/{id}` | Obtener un producto por ID. |
| `PUT` | `/products/{id}` | Actualizar un producto. |
| `DELETE` | `/products/{id}` | Eliminar un producto. |

Parametros disponibles en `/products/all`:

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
GET /products/all?page=0&size=10&sortBy=price&sortDirection=desc
```

### Categorias

| Metodo | Endpoint | Uso |
|--------|----------|-----|
| `POST` | `/categories` | Crear una categoria. |
| `GET` | `/categories/all` | Listar categorias. |
| `GET` | `/categories/{id}` | Buscar categoria por ID. |
| `GET` | `/categories/{name}` | Buscar categoria por nombre. |
| `PUT` | `/categories/{id}` | Actualizar una categoria. |
| `DELETE` | `/categories/{id}` | Eliminar una categoria. |

### Marcas

| Metodo | Endpoint | Uso |
|--------|----------|-----|
| `POST` | `/brands` | Crear una marca. |
| `GET` | `/brands/all` | Listar marcas. |
| `GET` | `/brands/{id}` | Buscar marca por ID. |
| `GET` | `/brands/{name}` | Buscar marca por nombre. |
| `PUT` | `/brands/{id}` | Actualizar una marca. |
| `DELETE` | `/brands/{id}` | Eliminar una marca. |

### Ventas

| Metodo | Endpoint | Uso |
|--------|----------|-----|
| `POST` | `/sales` | Crear una venta. |
| `GET` | `/sales` | Consultar ventas con paginacion/filtros. |
| `GET` | `/sales/{id}` | Obtener una venta por ID. |

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

## Datos iniciales

El archivo `src/main/resources/data.sql` contiene datos de ejemplo, pero no se ejecuta automaticamente en produccion salvo que se habilite explicitamente la inicializacion SQL de Spring.

Si `/products/all` responde con `content: []`, la API funciona pero la base esta vacia.

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
