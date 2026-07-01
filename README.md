[English](README.en.md) | [Español](README.md)

# API de comercio electrónico

API REST para un backend simple de comercio electrónico: autenticación, usuarios, productos, categorías, marcas y ventas. No hay frontend; Swagger UI es el punto de entrada principal para explorar la API.

## Ruta rápida

1. Inicia PostgreSQL en local: `docker compose up -d`
2. Ejecuta la aplicación:
   - macOS/Linux: `./mvnw spring-boot:run`
   - Windows: `mvnw.cmd spring-boot:run`
3. Abre Swagger UI: `http://localhost:8080/swagger-ui.html`
4. Verifica que la app esté saludable: `curl http://localhost:8080/actuator/health`

## Qué ofrece el proyecto

| Área | Propósito |
|------|-----------|
| Autenticación | Inicio de sesión, renovación de token y cierre de sesión con cookies JWT |
| Usuarios | Registro de usuarios y consulta del perfil autenticado |
| Productos | Crear, listar, actualizar y eliminar productos |
| Categorías | CRUD y listado paginado |
| Marcas | CRUD y listado paginado |
| Ventas | Crear ventas y consultar ventas por rango de fechas |

## Requisitos previos

| Requisito | Notas |
|-----------|-------|
| Java 21 | Requerido por la compilación de Spring Boot |
| Docker | Se usa para PostgreSQL en local y para ejecuciones en contenedor |
| Maven Wrapper | Incluido en el repositorio; no hace falta instalar Maven por separado |

## Configuración local

El perfil predeterminado es `dev` (`src/main/resources/application.properties`), por lo que las ejecuciones locales esperan PostgreSQL en `localhost:5433` con estos valores:

| Configuración | Valor |
|--------------|-------|
| Base de datos | `DB_ecommerce` |
| Usuario | `user` |
| Contraseña | `123456` |

### 1) Iniciar la base de datos

```bash
docker compose up -d
```

### 2) Iniciar la API

```bash
# macOS/Linux
./mvnw spring-boot:run

# Windows
mvnw.cmd spring-boot:run
```

La API está disponible en `http://localhost:8080`.

### 3) Paso opcional de compilación

```bash
# macOS/Linux
./mvnw clean package

# Windows
mvnw.cmd clean package
```

El JAR generado se escribe en `target/`.

## Modos alternativos de ejecución

### Stack completo con Docker Compose

`docker compose up -d` también define el servicio de la app, por lo que puede ejecutar la base de datos y la API juntas desde el `Dockerfile`.

### Despliegue en Render

El repositorio incluye `render.yaml` y `Dockerfile` para desplegar en Render con el perfil `prod`.

Las entradas de producción requeridas están definidas en `src/main/resources/application-prod.properties` y `render.yaml`, e incluyen:

- `SPRING_PROFILES_ACTIVE=prod`
- `RENDER_DATABASE_HOST`
- `RENDER_DATABASE_PORT`
- `RENDER_DATABASE_NAME`
- `RENDER_DATABASE_USER`
- `RENDER_DATABASE_PASSWORD`
- `JWT_SECRET`

## Resultado esperado y verificación

Después del inicio:

- Swagger UI debería cargar en `http://localhost:8080/swagger-ui.html`
- La verificación de salud debería devolver `UP` en `http://localhost:8080/actuator/health`
- `GET /api/v1/products/all` debería devolver una respuesta JSON paginada (un contenido vacío es válido si no existen productos)

## Resolución de problemas

| Síntoma | Causa probable / solución |
|---------|---------------------------|
| La aplicación no puede conectarse a PostgreSQL | Inicia primero la base de datos y confirma que el puerto `5433` esté libre |
| Las cookies de inicio de sesión no persisten en local | La configuración local define `app.security.cookie.secure=false`, así que prueba sobre HTTP en `localhost` |
| Swagger no aparece en producción | Swagger está deshabilitado en `application-prod.properties` |
| El despliegue en Render falla al arrancar | Revisa `JWT_SECRET` y las variables de entorno de la base de datos en Render |

## Notas

- La API usa UTC como zona horaria JVM predeterminada.
- La persistencia local está configurada mediante JPA con `ddl-auto=update`.
- La exposición de Actuator está limitada al endpoint de salud.
