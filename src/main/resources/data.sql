TRUNCATE TABLE products CASCADE;
TRUNCATE TABLE brands CASCADE;
TRUNCATE TABLE categories CASCADE;

-- IMPORTANTE: Para que este archivo se ejecute automáticamente, spring.sql.init.mode=always debe estar configurado en application.properties

-- ==============================================
-- 1. Inserción de Categorías (categories)
-- ==============================================
INSERT INTO categories (id, name, created_at, created_by, updated_at, last_modified_by) VALUES
(1, 'Calzado Deportivo', CURRENT_TIMESTAMP, 'system_init', CURRENT_TIMESTAMP, 'system_init'),
(2, 'Ropa de Entrenamiento', CURRENT_TIMESTAMP, 'system_init', CURRENT_TIMESTAMP, 'system_init'),
(3, 'Accesorios', CURRENT_TIMESTAMP, 'system_init', CURRENT_TIMESTAMP, 'system_init'),
(4, 'Electrónica Deportiva', CURRENT_TIMESTAMP, 'system_init', CURRENT_TIMESTAMP, 'system_init'),
(5, 'Suplementos', CURRENT_TIMESTAMP, 'system_init', CURRENT_TIMESTAMP, 'system_init')
ON CONFLICT (id) DO NOTHING;



-- ==============================================
-- 2. Inserción de Marcas (brands)
-- ==============================================
INSERT INTO brands (id, name, created_at, created_by, updated_at, last_modified_by) VALUES
(1, 'Nike', CURRENT_TIMESTAMP, 'system_init', CURRENT_TIMESTAMP, 'system_init'),
(2, 'Adidas', CURRENT_TIMESTAMP, 'system_init', CURRENT_TIMESTAMP, 'system_init'),
(3, 'Puma', CURRENT_TIMESTAMP, 'system_init', CURRENT_TIMESTAMP, 'system_init'),
(4, 'Under Armour', CURRENT_TIMESTAMP, 'system_init', CURRENT_TIMESTAMP, 'system_init'),
(5, 'Reebok', CURRENT_TIMESTAMP, 'system_init', CURRENT_TIMESTAMP, 'system_init'),
(6, 'Garmin', CURRENT_TIMESTAMP, 'system_init', CURRENT_TIMESTAMP, 'system_init'),
(7, 'Optimum Nutrition', CURRENT_TIMESTAMP, 'system_init', CURRENT_TIMESTAMP, 'system_init')
ON CONFLICT (id) DO NOTHING;

-- ==============================================
-- 3. Inserción de Productos (products)
-- ==============================================
-- (active, brand_id, category_id, created_at, created_by, description, last_modified_by, name, price, stock, updated_at, version)

INSERT INTO products (id, name, description, brand_id, category_id, price, stock, active, version, created_at, created_by, updated_at, last_modified_by) VALUES
-- Calzado (Categoría 1)
(1, 'Air Max 270', 'Zapatillas de running con cámara de aire visible para máxima amortiguación.', 1, 1, 150.00, 50, true, 0, CURRENT_TIMESTAMP, 'system_init', CURRENT_TIMESTAMP, 'system_init'),
(2, 'Ultraboost 22', 'Zapatillas running con tecnología de retorno de energía líder en la industria.', 2, 1, 180.00, 30, true, 0, CURRENT_TIMESTAMP, 'system_init', CURRENT_TIMESTAMP, 'system_init'),
(3, 'Suede Classic', 'El diseño clásico que definió la era del calzado urbano.', 3, 1, 85.00, 120, true, 0, CURRENT_TIMESTAMP, 'system_init', CURRENT_TIMESTAMP, 'system_init'),

-- Ropa (Categoría 2)
(4, 'Dri-FIT T-Shirt', 'Camiseta de entrenamiento transpirable que absorbe el sudor.', 1, 2, 35.00, 200, true, 0, CURRENT_TIMESTAMP, 'system_init', CURRENT_TIMESTAMP, 'system_init'),
(5, 'Tiro 21 Track Pants', 'Pantalones de entrenamiento ajustados para fútbol y gimnasio.', 2, 2, 50.00, 150, true, 0, CURRENT_TIMESTAMP, 'system_init', CURRENT_TIMESTAMP, 'system_init'),
(6, 'Project Rock Shorts', 'Pantalones cortos de entrenamiento resistentes, probados por Dwayne Johnson.', 4, 2, 60.00, 80, true, 0, CURRENT_TIMESTAMP, 'system_init', CURRENT_TIMESTAMP, 'system_init'),

-- Accesorios (Categoría 3)
(7, 'Duffel Bag Medium', 'Bolsa de deporte espaciosa con compartimento para zapatillas.', 1, 3, 45.00, 60, true, 0, CURRENT_TIMESTAMP, 'system_init', CURRENT_TIMESTAMP, 'system_init'),
(8, 'Training Gloves', 'Guantes de levantamiento de pesas con agarre reforzado.', 5, 3, 25.00, 100, true, 0, CURRENT_TIMESTAMP, 'system_init', CURRENT_TIMESTAMP, 'system_init'),
(9, 'Water Bottle 1L', 'Botella de agua libre de BPA con indicador de medida.', 4, 3, 15.00, 300, true, 0, CURRENT_TIMESTAMP, 'system_init', CURRENT_TIMESTAMP, 'system_init'),

-- Electrónica (Categoría 4)
(10, 'Forerunner 245', 'Reloj inteligente con GPS diseñado específicamente para corredores.', 6, 4, 300.00, 20, true, 0, CURRENT_TIMESTAMP, 'system_init', CURRENT_TIMESTAMP, 'system_init'),
(11, 'Vivosmart 5', 'Pulsera de actividad inteligente con monitor de energía corporal.', 6, 4, 150.00, 40, true, 0, CURRENT_TIMESTAMP, 'system_init', CURRENT_TIMESTAMP, 'system_init'),

-- Suplementos (Categoría 5)
(12, 'Gold Standard Whey 2lbs', 'Proteína de suero de leche en polvo, sabor chocolate doble.', 7, 5, 35.99, 100, true, 0, CURRENT_TIMESTAMP, 'system_init', CURRENT_TIMESTAMP, 'system_init'),
(13, 'Amino Energy', 'Suplemento de aminoácidos con cafeína de fuentes naturales.', 7, 5, 22.50, 85, true, 0, CURRENT_TIMESTAMP, 'system_init', CURRENT_TIMESTAMP, 'system_init')
ON CONFLICT (id) DO NOTHING;

-- Para PostgreSQL, actualizamos la secuencia del ID
-- SELECT setval('products_id_seq', (SELECT MAX(id) FROM products));
