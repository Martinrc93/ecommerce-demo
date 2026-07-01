# E-commerce API

REST API for a simple e-commerce backend: authentication, users, products, categories, brands, and sales. There is no frontend; Swagger UI is the main entry point for exploring the API.

## Quick path

1. Start PostgreSQL locally: `docker compose up -d`
2. Run the application:
   - macOS/Linux: `./mvnw spring-boot:run`
   - Windows: `mvnw.cmd spring-boot:run`
3. Open Swagger UI: `http://localhost:8080/swagger-ui.html`
4. Verify the app is healthy: `curl http://localhost:8080/actuator/health`

## What the project provides

| Area | Purpose |
|------|---------|
| Authentication | Login, token refresh, and logout using JWT cookies |
| Users | Register a user and fetch the authenticated profile |
| Products | Create, list, update, and delete products |
| Categories | CRUD and paginated listing |
| Brands | CRUD and paginated listing |
| Sales | Create sales and query sales by date range |

## Prerequisites

| Requirement | Notes |
|-------------|-------|
| Java 21 | Required by the Spring Boot build |
| Docker | Used for PostgreSQL locally and for containerized runs |
| Maven Wrapper | Included in the repo; no separate Maven install is needed |

## Local setup

The default profile is `dev` (`src/main/resources/application.properties`), so local runs expect PostgreSQL on `localhost:5433` with these values:

| Setting | Value |
|---------|-------|
| Database | `DB_ecommerce` |
| Username | `user` |
| Password | `123456` |

### 1) Start the database

```bash
docker compose up -d
```

### 2) Start the API

```bash
# macOS/Linux
./mvnw spring-boot:run

# Windows
mvnw.cmd spring-boot:run
```

The API is available at `http://localhost:8080`.

### 3) Optional build step

```bash
# macOS/Linux
./mvnw clean package

# Windows
mvnw.cmd clean package
```

The packaged JAR is written to `target/`.

## Alternative run modes

### Full Docker Compose stack

`docker compose up -d` also defines the app service, so it can run the database and API together from the `Dockerfile`.

### Render deployment

The repo includes `render.yaml` and `Dockerfile` for deploying to Render with the `prod` profile.

Required production inputs are defined in `src/main/resources/application-prod.properties` and `render.yaml`, including:

- `SPRING_PROFILES_ACTIVE=prod`
- `RENDER_DATABASE_HOST`
- `RENDER_DATABASE_PORT`
- `RENDER_DATABASE_NAME`
- `RENDER_DATABASE_USER`
- `RENDER_DATABASE_PASSWORD`
- `JWT_SECRET`

## Expected result and verification

After startup:

- Swagger UI should load at `http://localhost:8080/swagger-ui.html`
- Health check should return `UP` at `http://localhost:8080/actuator/health`
- `GET /api/v1/products/all` should return a paginated JSON response (empty content is valid if no products exist)

## Troubleshooting

| Symptom | Likely cause / fix |
|---------|--------------------|
| App cannot connect to PostgreSQL | Start the database first and confirm port `5433` is free |
| Login cookies do not persist locally | Local config sets `app.security.cookie.secure=false`, so test over HTTP on `localhost` |
| Swagger does not appear in production | Swagger is disabled in `application-prod.properties` |
| Render deployment fails on startup | Check `JWT_SECRET` and the Render database env vars |

## Notes

- The API uses UTC as the default JVM time zone.
- Local persistence is configured through JPA with `ddl-auto=update`.
- Actuator exposure is limited to the health endpoint.
