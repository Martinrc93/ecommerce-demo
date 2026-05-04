# E-Commerce API

Una API REST robusta para la gestión de ventas y productos de un sistema de comercio electrónico (E-commerce). Este proyecto está diseñado con un enfoque en la escalabilidad, mantenibilidad y buenas prácticas de ingeniería de software.

## 🏗 Arquitectura y Diseño

El proyecto sigue estrictamente la **Arquitectura Hexagonal** (también conocida como Arquitectura de Puertos y Adaptadores) y principios de **Domain-Driven Design (DDD)**. 

Esto permite que la lógica central del negocio esté completamente aislada de los detalles técnicos (como la base de datos, el framework web o librerías de mapeo).

### Estructura de Capas
El código está dividido principalmente en tres capas concéntricas:

1.  **Domain (`com.demo.ecommerce.domain`)**:
    *   Contiene la lógica core del negocio.
    *   **Entidades**: `Sale`, `SaleDetail`, `Product`.
    *   **Value Objects (Objetos de Valor)**: `Money`, `Discount`. Garantizan la inmutabilidad y encapsulan reglas de negocio específicas (ej. un descuento no puede ser mayor a 100%, el dinero no puede ser negativo).
    *   Esta capa *no tiene dependencias* de Spring ni de la base de datos.

2.  **Application (`com.demo.ecommerce.application`)**:
    *   Contiene los casos de uso del sistema.
    *   **Ports (Puertos)**: Interfaces que definen cómo el mundo exterior interactúa con la aplicación (`In`) y cómo la aplicación interactúa con infraestructuras externas (`Out`, como repositorios).
    *   **Services**: Implementación de los casos de uso (ej. `CreateSaleService`, `GetSaleService`). Orquestan las entidades del dominio para cumplir una funcionalidad.

3.  **Infrastructure (`com.demo.ecommerce.infrastructure`)**:
    *   Contiene los detalles de implementación técnica (los "Adaptadores").
    *   **Input (Web)**: Controladores REST (`SaleController`), DTOs y Mappers (`MapStruct`) para recibir peticiones HTTP.
    *   **Output (Persistence)**: Entidades JPA (`SaleEntity`), Repositorios de Spring Data, y adaptadores que implementan los puertos de salida (`SaleRepositoryAdapter`).

## 🛠 Tecnologías Utilizadas

*   **Java 21+** (Asumido por las características modernas utilizadas).
*   **Spring Boot 3.x**: Framework principal para inyección de dependencias, REST y configuración.
*   **Spring Data JPA / Hibernate**: ORM para la persistencia de datos.
*   **PostgreSQL**: Base de datos relacional.
*   **MapStruct**: Generación automática de código para mapeo seguro entre Entidades JPA, Modelos de Dominio y DTOs.
*   **Lombok**: Reducción de código repetitivo (boilterplate) como getters, setters, y constructores.

## 🚀 Endpoints de la API (Ventas)

A continuación, se detallan los endpoints disponibles para la gestión de ventas (`/sales`):

### 1. Crear una Venta
*   **Ruta**: `POST /sales`
*   **Descripción**: Registra una nueva venta, actualizando el stock de los productos involucrados y calculando totales y subtotales.
*   **Cuerpo de la Petición (JSON)**:
    ```json
    {
      "userId": "a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11",
      "Discount": "10.00",
      "items": [
        {
          "productId": 1,
          "quantity": 1
        },
        {
          "productId": 2,
          "quantity": 2
        }
      ]
    }
    ```
*   **Respuesta Exitosa**: `201 Created` - `Sale created successfully with ID: 1`

### 2. Obtener una Venta por ID
*   **Ruta**: `GET /sales/{id}`
*   **Descripción**: Recupera los detalles de una venta específica mediante su identificador único.
*   **Parámetro de Ruta**: `id` (Long).
*   **Respuesta Exitosa**: `200 OK`
    ```json
    {
      "id": 1,
      "userId": "a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11",
      "items": [
        {
          "quantity": 1,
          "price": 299.99,
          "discount": 0.00
        }
      ],
      "subTotal": 299.99,
      "discount": 10.00,
      "total": 269.99
    }
    ```

### 3. Obtener Ventas por Rango de Fechas (Paginado)
*   **Ruta**: `GET /sales`
*   **Descripción**: Obtiene un listado paginado de ventas que ocurrieron dentro de un rango de fechas.
*   **Parámetros de Consulta (Query Params)**:
    *   `startDate` (Requerido): Fecha de inicio en formato ISO-8601 (ej. `2023-10-01T00:00:00`).
    *   `endDate` (Requerido): Fecha de fin en formato ISO-8601 (ej. `2023-10-31T23:59:59`).
    *   `page` (Opcional): Número de página (por defecto `0`).
    *   `size` (Opcional): Cantidad de registros por página (por defecto `10`).
*   **Ejemplo de URL**: `/sales?startDate=2023-10-01T00:00:00&endDate=2023-10-31T23:59:59&page=0&size=10`
*   **Respuesta Exitosa**: `200 OK` (Objeto `Page` de Spring conteniendo la lista de ventas).

## ⚙️ Cómo ejecutar el proyecto

1.  Asegúrate de tener **Java JDK** y **Maven** instalados.
2.  Configura una base de datos PostgreSQL localmente o mediante Docker.
3.  Ajusta las credenciales de base de datos en el archivo `src/main/resources/application.properties` o `application.yml`.
4.  Ejecuta el siguiente comando para limpiar, compilar (y generar las clases de MapStruct) y empaquetar el proyecto:
    ```bash
    mvn clean install
    ```
5.  Levanta la aplicación:
    ```bash
    mvn spring-boot:run
    ```

## 📝 Notas de Desarrollo

*   **Mapeo de Datos**: Los mappers (`SaleDtoMapper`, `SaleMapper`) han sido cuidadosamente configurados para lidiar con el aislamiento de la capa de dominio. Transforman automáticamente tipos primitivos (`BigDecimal`) en Objetos de Valor (`Money`, `Discount`) de forma transparente gracias a los métodos `default` incluidos en las interfaces.
*   **Gestión de Excepciones**: El dominio protege su propia integridad mediante validaciones internas (ej. lanzar `IllegalArgumentException` en los constructores de los `record` de Value Objects si los datos son inválidos).