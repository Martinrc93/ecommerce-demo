# E-Commerce API

Una API REST robusta para la gestión de ventas, productos y usuarios de un sistema de comercio electrónico (E-commerce). Este proyecto está diseñado con un enfoque en la escalabilidad, mantenibilidad y buenas prácticas de ingeniería de software.

## 🏗 Arquitectura y Diseño

El proyecto sigue estrictamente la **Arquitectura Hexagonal** (también conocida como Arquitectura de Puertos y Adaptadores) y principios de **Domain-Driven Design (DDD)**. 

Esto permite que la lógica central del negocio esté completamente aislada de los detalles técnicos (como la base de datos, el framework web o librerías de mapeo).

### Estructura de Capas
El código está dividido principalmente en tres capas concéntricas:

1.  **Domain (`com.demo.ecommerce.domain`)**:
    *   Contiene la lógica core del negocio.
    *   **Entidades**: `Sale`, `SaleDetail`, `Product`, `User`.
    *   **Value Objects (Objetos de Valor)**: `Money`, `Discount`. Garantizan la inmutabilidad y encapsulan reglas de negocio específicas (ej. un descuento no puede ser mayor a 100%, el dinero no puede ser negativo).
    *   Esta capa *no tiene dependencias* de Spring ni de la base de datos.

2.  **Application (`com.demo.ecommerce.application`)**:
    *   Contiene los casos de uso del sistema.
    *   **Ports (Puertos)**: Interfaces que definen cómo el mundo exterior interactúa con la aplicación (`In`) y cómo la aplicación interactúa con infraestructuras externas (`Out`, como repositorios).
    *   **Services**: Implementación de los casos de uso (ej. `CreateSaleService`, `GetSaleService`, `AuthService`). Orquestan las entidades del dominio para cumplir una funcionalidad.

3.  **Infrastructure (`com.demo.ecommerce.infrastructure`)**:
    *   Contiene los detalles de implementación técnica (los "Adaptadores").
    *   **Input (Web)**: Controladores REST (`SaleController`, `ProductController`, `UserController`, `AuthController`), DTOs y Mappers (`MapStruct`) para recibir peticiones HTTP.
    *   **Output (Persistence)**: Entidades JPA (`SaleEntity`, `ProductEntity`, etc.), Repositorios de Spring Data, y adaptadores que implementan los puertos de salida.
    *   **Security**: Implementación de seguridad basada en Spring Security y JWT (JSON Web Tokens).

## 🛠 Tecnologías Utilizadas

*   **Java 21+**
*   **Spring Boot 3.x**: Framework principal para inyección de dependencias, REST y configuración.
*   **Spring Security & JWT**: Gestión segura de autenticación y autorización.
*   **Spring Data JPA / Hibernate**: ORM para la persistencia de datos.
*   **PostgreSQL**: Base de datos relacional.
*   **MapStruct**: Generación automática de código para mapeo seguro entre Entidades JPA, Modelos de Dominio y DTOs.
*   **Lombok**: Reducción de código repetitivo (boilerplate).
*   **Springdoc OpenAPI (Swagger)**: Documentación de la API autogenerada e interactiva.

## 📖 Documentación de la API (Swagger)

Este proyecto utiliza **Springdoc OpenAPI** para generar automáticamente la documentación de la API REST.

Una vez que la aplicación esté en ejecución, puedes acceder a la interfaz gráfica de Swagger UI navegando a:
👉 `http://localhost:8080/swagger-ui.html`

O si necesitas acceder al esquema JSON directamente (útil para IA, Postman o clientes generados automáticamente):
👉 `http://localhost:8080/v3/api-docs`

### Características de nuestra documentación en Swagger:
*   **Anotaciones Detalladas**: En los controladores usamos anotaciones como `@Tag` (para agrupar rutas) y `@Operation(summary="...", description="...")` para explicar el comportamiento exacto de cada endpoint.
*   **Esquemas Automáticos (Schemas)**: Los objetos de transferencia de datos (DTOs) se documentan solos. Swagger lee nuestros `records` de Java y muestra automáticamente qué campos son requeridos, cuáles son opcionales y el tipo de dato esperado.
*   **Ocultamiento de Parámetros Internos**: Se utilizan anotaciones como `@Parameter(hidden = true)` para evitar que Swagger intente documentar objetos inyectados automáticamente por Spring (como `UserDetails` o `Pageable`), garantizando una interfaz limpia y libre de errores.

## 📝 Notas de Desarrollo

*   **Mapeo de Datos**: Los mappers (`SaleDtoMapper`, `SaleMapper`) transforman automáticamente tipos primitivos (`BigDecimal`) en Objetos de Valor (`Money`, `Discount`) gracias a los métodos `default` en las interfaces.
*   **N+1 Queries**: Se han tomado medidas en las consultas JPA (como el uso de `JOIN FETCH` o evitando loops de escritura) para garantizar que el ORM no degrade el rendimiento al cargar colecciones perezosas (Lazy Loading).
*   **Autenticación**: Los endpoints están protegidos por tokens JWT. El flujo incluye login, registro, refresco de tokens (mediante HTTPOnly Cookies) y logout seguro.