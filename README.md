# E-commerce API

API RESTful para la gestión de un sistema de comercio electrónico, desarrollada con Spring Boot 3 y Java 21. Incluye gestión de productos, ventas y autenticación segura con JWT.

## Requisitos Previos

- Java 21 o superior
- Maven 3.8+
- Base de datos PostgreSQL (solo para entorno de producción)
- Entorno de desarrollo compatible (IntelliJ IDEA, VS Code, etc.)

## Tecnologías Principales

- Spring Boot 3.x
- Spring Security (JWT)
- Spring Data JPA
- PostgreSQL & H2 Database
- MapStruct (Mapeo de objetos)
- Swagger / OpenAPI 3 (Documentación interactiva)

## Configuración y Perfiles

La aplicación está preparada para ejecutarse en diferentes entornos utilizando los perfiles de Spring Boot (`dev` y `prod`). Por defecto, la aplicación se ejecuta en el entorno de desarrollo.

### Entorno de Desarrollo (`dev`)

El perfil de desarrollo está configurado en `src/main/resources/application-dev.properties`.

- **Base de Datos:** H2 en memoria (no requiere instalación).
- **Consola H2:** Habilitada en `/h2-console`.
- **Swagger:** Habilitado. Disponible en `/swagger-ui.html`.
- **Logs:** Nivel `DEBUG` para seguimiento de errores.

### Entorno de Producción (`prod`)

El perfil de producción está configurado en `src/main/resources/application-prod.properties` y requiere configuración de variables de entorno para funcionar de forma segura.

- **Base de Datos:** PostgreSQL.
- **Swagger:** Deshabilitado por seguridad.
- **Logs:** Nivel `WARN`.

#### Variables de entorno requeridas para producción:

Para ejecutar la aplicación en producción, debes definir las siguientes variables de entorno:

- `DB_URL`: URL de conexión a la base de datos (ej. `jdbc:postgresql://localhost:5432/miproyecto`)
- `DB_USERNAME`: Usuario de la base de datos
- `DB_PASSWORD`: Contraseña de la base de datos
- `JWT_SECRET`: Clave secreta fuerte para firmar los tokens JWT (mínimo 256 bits/32 caracteres).
- `COOKIE_DOMAIN`: Dominio de tu aplicación para la configuración segura de cookies (ej. `midominio.com`).

Opcionalmente, puedes configurar:
- `JWT_ACCESS_EXPIRATION`: Tiempo de expiración del token de acceso en milisegundos (por defecto 15 minutos).
- `JWT_REFRESH_EXPIRATION`: Tiempo de expiración del token de refresco en milisegundos (por defecto 30 minutos).

## Cómo Ejecutar el Proyecto

### 1. Clonar el repositorio
```bash
git clone <url-del-repositorio>
cd ecommerce
```

### 2. Ejecutar en modo desarrollo
Por defecto, la aplicación se inicia con el perfil `dev`. Simplemente ejecuta:

```bash
# Con Maven Wrapper (Linux/Mac)
./mvnw spring-boot:run

# Con Maven Wrapper (Windows)
mvnw.cmd spring-boot:run
```

O también puedes compilar y ejecutar el `.jar` directamente:

```bash
mvn clean package
java -jar target/ecommerce-0.0.1-SNAPSHOT.jar
```

Una vez iniciada, podrás acceder a la documentación interactiva (Swagger UI) en:
[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

### 3. Ejecutar en modo producción

Primero, debes compilar la aplicación asegurándote de definir las variables de entorno previamente:

```bash
# Ejemplo definiendo variables localmente en terminal
export DB_URL=jdbc:postgresql://localhost:5432/prod_db
export DB_USERNAME=tu_usuario
export DB_PASSWORD=tu_password
export JWT_SECRET=tu_secreto_muy_largo_y_seguro_para_jwt
export COOKIE_DOMAIN=tudominio.com

# Iniciar la aplicación con el perfil 'prod'
java -jar target/ecommerce-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod
```

## Estructura del Proyecto

El proyecto sigue una arquitectura en capas adaptada a los principios de arquitectura limpia/hexagonal:

- `infrastructure/input/web`: Controladores REST y DTOs (Request/Response).
- `application`: Casos de uso y lógica de negocio principal.
- `domain`: Modelos de negocio.
- `infrastructure/output/persistence`: Entidades JPA, repositorios y adaptadores de base de datos.
- `infrastructure/config`: Configuraciones generales de la aplicación (Seguridad, Swagger, etc.).

## Endpoints Principales

- `/api/auth/login`: Autenticación de usuarios.
- `/api/products`: Gestión de productos (CRUD).
- `/api/sales`: Registro y consulta de ventas.
