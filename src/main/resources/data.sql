TRUNCATE TABLE auth, sale_details, sales, products, brands, categories, users RESTART IDENTITY CASCADE;

-- Plain development seed executed by POST /api/v1/sales/seed.
-- Sales dates are distributed between now and the previous 7 days.

-- ==============================================
-- Categories
-- ==============================================
INSERT INTO categories (id, name, created_at, created_by, updated_at, last_modified_by) VALUES
(1, 'Running', CURRENT_TIMESTAMP, 'seed', CURRENT_TIMESTAMP, 'seed'),
(2, 'Training', CURRENT_TIMESTAMP, 'seed', CURRENT_TIMESTAMP, 'seed'),
(3, 'Lifestyle', CURRENT_TIMESTAMP, 'seed', CURRENT_TIMESTAMP, 'seed'),
(4, 'Outdoor', CURRENT_TIMESTAMP, 'seed', CURRENT_TIMESTAMP, 'seed'),
(5, 'Football', CURRENT_TIMESTAMP, 'seed', CURRENT_TIMESTAMP, 'seed'),
(6, 'Accessories', CURRENT_TIMESTAMP, 'seed', CURRENT_TIMESTAMP, 'seed'),
(7, 'Electronics', CURRENT_TIMESTAMP, 'seed', CURRENT_TIMESTAMP, 'seed'),
(8, 'Nutrition', CURRENT_TIMESTAMP, 'seed', CURRENT_TIMESTAMP, 'seed');

-- ==============================================
-- Brands
-- ==============================================
INSERT INTO brands (id, name, created_at, created_by, updated_at, last_modified_by) VALUES
(1, 'Nike', CURRENT_TIMESTAMP, 'seed', CURRENT_TIMESTAMP, 'seed'),
(2, 'Adidas', CURRENT_TIMESTAMP, 'seed', CURRENT_TIMESTAMP, 'seed'),
(3, 'Puma', CURRENT_TIMESTAMP, 'seed', CURRENT_TIMESTAMP, 'seed'),
(4, 'Under Armour', CURRENT_TIMESTAMP, 'seed', CURRENT_TIMESTAMP, 'seed'),
(5, 'Reebok', CURRENT_TIMESTAMP, 'seed', CURRENT_TIMESTAMP, 'seed'),
(6, 'Garmin', CURRENT_TIMESTAMP, 'seed', CURRENT_TIMESTAMP, 'seed'),
(7, 'Asics', CURRENT_TIMESTAMP, 'seed', CURRENT_TIMESTAMP, 'seed'),
(8, 'New Balance', CURRENT_TIMESTAMP, 'seed', CURRENT_TIMESTAMP, 'seed'),
(9, 'Salomon', CURRENT_TIMESTAMP, 'seed', CURRENT_TIMESTAMP, 'seed'),
(10, 'Wilson', CURRENT_TIMESTAMP, 'seed', CURRENT_TIMESTAMP, 'seed'),
(11, 'Optimum Nutrition', CURRENT_TIMESTAMP, 'seed', CURRENT_TIMESTAMP, 'seed'),
(12, 'Hydro Flask', CURRENT_TIMESTAMP, 'seed', CURRENT_TIMESTAMP, 'seed');

-- ==============================================
-- Users
-- ==============================================
INSERT INTO users (id, name, last_name, email, password, rol) VALUES
('11111111-1111-1111-1111-111111111111', 'Admin', 'Seed', 'admin.seed@demo.com', '$2a$10$9R4PZmzQiTAJZBtNhMr5yerbdbUXTDRiby.EhHYwpl2MIfPwLtPMa', 'ADMIN'),
('22222222-2222-2222-2222-222222222221', 'Lucas', 'Diaz', 'lucas.diaz@demo.com', '$2a$10$n1y5ubDNUoPT7NnnOYk4DOlmg2Mw0NUQKw2ksUsHbdGtG3ZENHK6C', 'BUYER'),
('22222222-2222-2222-2222-222222222222', 'Mia', 'Suarez', 'mia.suarez@demo.com', '$2a$10$n1y5ubDNUoPT7NnnOYk4DOlmg2Mw0NUQKw2ksUsHbdGtG3ZENHK6C', 'BUYER'),
('22222222-2222-2222-2222-222222222223', 'Mateo', 'Gomez', 'mateo.gomez@demo.com', '$2a$10$n1y5ubDNUoPT7NnnOYk4DOlmg2Mw0NUQKw2ksUsHbdGtG3ZENHK6C', 'BUYER'),
('22222222-2222-2222-2222-222222222224', 'Emma', 'Lopez', 'emma.lopez@demo.com', '$2a$10$n1y5ubDNUoPT7NnnOYk4DOlmg2Mw0NUQKw2ksUsHbdGtG3ZENHK6C', 'BUYER'),
('22222222-2222-2222-2222-222222222225', 'Thiago', 'Martinez', 'thiago.martinez@demo.com', '$2a$10$n1y5ubDNUoPT7NnnOYk4DOlmg2Mw0NUQKw2ksUsHbdGtG3ZENHK6C', 'BUYER'),
('22222222-2222-2222-2222-222222222226', 'Olivia', 'Fernandez', 'olivia.fernandez@demo.com', '$2a$10$n1y5ubDNUoPT7NnnOYk4DOlmg2Mw0NUQKw2ksUsHbdGtG3ZENHK6C', 'BUYER'),
('22222222-2222-2222-2222-222222222227', 'Benjamin', 'Perez', 'benjamin.perez@demo.com', '$2a$10$n1y5ubDNUoPT7NnnOYk4DOlmg2Mw0NUQKw2ksUsHbdGtG3ZENHK6C', 'BUYER'),
('22222222-2222-2222-2222-222222222228', 'Sofia', 'Rodriguez', 'sofia.rodriguez@demo.com', '$2a$10$n1y5ubDNUoPT7NnnOYk4DOlmg2Mw0NUQKw2ksUsHbdGtG3ZENHK6C', 'BUYER'),
('22222222-2222-2222-2222-222222222229', 'Valentino', 'Torres', 'valentino.torres@demo.com', '$2a$10$n1y5ubDNUoPT7NnnOYk4DOlmg2Mw0NUQKw2ksUsHbdGtG3ZENHK6C', 'BUYER');

-- ==============================================
-- Products
-- ==============================================
INSERT INTO products (id, name, description, brand_id, category_id, price, stock, active, version, created_at, created_by, updated_at, last_modified_by) VALUES
(1, 'Air Zoom Pegasus 41', 'Neutral running shoes for daily road training.', 1, 1, 139.99, 45, true, 0, CURRENT_TIMESTAMP, 'seed', CURRENT_TIMESTAMP, 'seed'),
(2, 'Ultraboost Light', 'Responsive running shoes with cushioned support.', 2, 1, 189.99, 32, true, 0, CURRENT_TIMESTAMP, 'seed', CURRENT_TIMESTAMP, 'seed'),
(3, 'Gel Nimbus 26', 'Premium cushioned running shoes for long distance.', 7, 1, 159.99, 28, true, 0, CURRENT_TIMESTAMP, 'seed', CURRENT_TIMESTAMP, 'seed'),
(4, 'Fresh Foam 1080', 'Soft daily trainer with a breathable knit upper.', 8, 1, 164.99, 34, true, 0, CURRENT_TIMESTAMP, 'seed', CURRENT_TIMESTAMP, 'seed'),
(5, 'Velocity Nitro 3', 'Lightweight running shoes for speed sessions.', 3, 1, 119.99, 22, true, 0, CURRENT_TIMESTAMP, 'seed', CURRENT_TIMESTAMP, 'seed'),
(6, 'Metcon 9', 'Stable training shoes for lifting and gym workouts.', 1, 2, 149.99, 26, true, 0, CURRENT_TIMESTAMP, 'seed', CURRENT_TIMESTAMP, 'seed'),
(7, 'Dropset Trainer 2', 'Training shoes built for strength sessions.', 2, 2, 129.99, 30, true, 0, CURRENT_TIMESTAMP, 'seed', CURRENT_TIMESTAMP, 'seed'),
(8, 'Project Rock 6', 'Durable training shoes for high intensity workouts.', 4, 2, 159.99, 18, true, 0, CURRENT_TIMESTAMP, 'seed', CURRENT_TIMESTAMP, 'seed'),
(9, 'Nano X4', 'Versatile shoes for cross training and cardio.', 5, 2, 139.99, 25, true, 0, CURRENT_TIMESTAMP, 'seed', CURRENT_TIMESTAMP, 'seed'),
(10, 'Training Shorts', 'Lightweight shorts with quick dry fabric.', 4, 2, 44.99, 80, true, 0, CURRENT_TIMESTAMP, 'seed', CURRENT_TIMESTAMP, 'seed'),
(11, 'Air Force 1 Low', 'Classic lifestyle sneakers with leather upper.', 1, 3, 119.99, 40, true, 0, CURRENT_TIMESTAMP, 'seed', CURRENT_TIMESTAMP, 'seed'),
(12, 'Samba OG', 'Iconic terrace sneakers for everyday outfits.', 2, 3, 109.99, 36, true, 0, CURRENT_TIMESTAMP, 'seed', CURRENT_TIMESTAMP, 'seed'),
(13, 'Suede Classic XXI', 'Retro lifestyle sneakers with suede finish.', 3, 3, 84.99, 50, true, 0, CURRENT_TIMESTAMP, 'seed', CURRENT_TIMESTAMP, 'seed'),
(14, 'Club C 85', 'Minimal court inspired lifestyle sneakers.', 5, 3, 79.99, 44, true, 0, CURRENT_TIMESTAMP, 'seed', CURRENT_TIMESTAMP, 'seed'),
(15, 'Essentials Hoodie', 'Soft fleece hoodie for casual daily wear.', 2, 3, 64.99, 65, true, 0, CURRENT_TIMESTAMP, 'seed', CURRENT_TIMESTAMP, 'seed'),
(16, 'Speedcross 6', 'Trail running shoes with aggressive grip.', 9, 4, 144.99, 24, true, 0, CURRENT_TIMESTAMP, 'seed', CURRENT_TIMESTAMP, 'seed'),
(17, 'XA Pro 3D', 'Outdoor shoes for hiking and technical trails.', 9, 4, 154.99, 21, true, 0, CURRENT_TIMESTAMP, 'seed', CURRENT_TIMESTAMP, 'seed'),
(18, 'Trail Jacket', 'Water resistant jacket for outdoor training.', 8, 4, 129.99, 19, true, 0, CURRENT_TIMESTAMP, 'seed', CURRENT_TIMESTAMP, 'seed'),
(19, 'Hiking Backpack 25L', 'Compact backpack with multiple storage pockets.', 9, 4, 89.99, 27, true, 0, CURRENT_TIMESTAMP, 'seed', CURRENT_TIMESTAMP, 'seed'),
(20, 'Thermal Base Layer', 'Warm base layer for cold weather activities.', 4, 4, 54.99, 38, true, 0, CURRENT_TIMESTAMP, 'seed', CURRENT_TIMESTAMP, 'seed'),
(21, 'Mercurial Vapor 16', 'Firm ground football boots for fast players.', 1, 5, 249.99, 16, true, 0, CURRENT_TIMESTAMP, 'seed', CURRENT_TIMESTAMP, 'seed'),
(22, 'Predator Elite', 'Football boots focused on control and accuracy.', 2, 5, 259.99, 12, true, 0, CURRENT_TIMESTAMP, 'seed', CURRENT_TIMESTAMP, 'seed'),
(23, 'Future Ultimate', 'Flexible football boots for agile movement.', 3, 5, 219.99, 14, true, 0, CURRENT_TIMESTAMP, 'seed', CURRENT_TIMESTAMP, 'seed'),
(24, 'Match Football', 'Durable football for training sessions.', 10, 5, 34.99, 70, true, 0, CURRENT_TIMESTAMP, 'seed', CURRENT_TIMESTAMP, 'seed'),
(25, 'Shin Guards Pro', 'Lightweight guards with secure ankle support.', 2, 5, 24.99, 90, true, 0, CURRENT_TIMESTAMP, 'seed', CURRENT_TIMESTAMP, 'seed'),
(26, 'Duffle Bag 40L', 'Spacious gym bag with ventilated shoe pocket.', 1, 6, 54.99, 42, true, 0, CURRENT_TIMESTAMP, 'seed', CURRENT_TIMESTAMP, 'seed'),
(27, 'Training Gloves', 'Padded gloves for weightlifting and grip support.', 5, 6, 22.99, 95, true, 0, CURRENT_TIMESTAMP, 'seed', CURRENT_TIMESTAMP, 'seed'),
(28, 'Stainless Bottle 32oz', 'Insulated bottle for cold drinks during training.', 12, 6, 39.99, 88, true, 0, CURRENT_TIMESTAMP, 'seed', CURRENT_TIMESTAMP, 'seed'),
(29, 'Performance Socks 3 Pack', 'Breathable socks for training and running.', 8, 6, 17.99, 120, true, 0, CURRENT_TIMESTAMP, 'seed', CURRENT_TIMESTAMP, 'seed'),
(30, 'Yoga Mat 6mm', 'Cushioned mat for yoga, mobility and stretching.', 4, 6, 29.99, 60, true, 0, CURRENT_TIMESTAMP, 'seed', CURRENT_TIMESTAMP, 'seed'),
(31, 'Forerunner 265', 'GPS running watch with AMOLED display.', 6, 7, 449.99, 15, true, 0, CURRENT_TIMESTAMP, 'seed', CURRENT_TIMESTAMP, 'seed'),
(32, 'Venu 3', 'Smartwatch with health tracking and sport modes.', 6, 7, 399.99, 17, true, 0, CURRENT_TIMESTAMP, 'seed', CURRENT_TIMESTAMP, 'seed'),
(33, 'Heart Rate Monitor', 'Chest strap sensor for accurate heart rate data.', 6, 7, 89.99, 31, true, 0, CURRENT_TIMESTAMP, 'seed', CURRENT_TIMESTAMP, 'seed'),
(34, 'Wireless Earbuds Sport', 'Sweat resistant earbuds for workouts.', 4, 7, 99.99, 36, true, 0, CURRENT_TIMESTAMP, 'seed', CURRENT_TIMESTAMP, 'seed'),
(35, 'Bike Speed Sensor', 'Compact cycling sensor for speed tracking.', 6, 7, 39.99, 29, true, 0, CURRENT_TIMESTAMP, 'seed', CURRENT_TIMESTAMP, 'seed'),
(36, 'Gold Standard Whey 2lb', 'Whey protein powder with chocolate flavor.', 11, 8, 39.99, 55, true, 0, CURRENT_TIMESTAMP, 'seed', CURRENT_TIMESTAMP, 'seed'),
(37, 'Amino Energy', 'Amino acid supplement with natural caffeine.', 11, 8, 24.99, 48, true, 0, CURRENT_TIMESTAMP, 'seed', CURRENT_TIMESTAMP, 'seed'),
(38, 'Creatine Monohydrate', 'Creatine powder for strength and performance.', 11, 8, 19.99, 62, true, 0, CURRENT_TIMESTAMP, 'seed', CURRENT_TIMESTAMP, 'seed'),
(39, 'Protein Bar Box', 'Box of protein bars for recovery and snacks.', 11, 8, 29.99, 40, true, 0, CURRENT_TIMESTAMP, 'seed', CURRENT_TIMESTAMP, 'seed'),
(40, 'Electrolyte Tablets', 'Hydration tablets for endurance training.', 11, 8, 14.99, 75, true, 0, CURRENT_TIMESTAMP, 'seed', CURRENT_TIMESTAMP, 'seed');

-- ==============================================
-- Sales
-- ==============================================
INSERT INTO sales (user_id, subtotal, discount, total, date, created_at, created_by, updated_at, last_modified_by) VALUES
('22222222-2222-2222-2222-222222222221', 219.97, 0, 219.97, CURRENT_TIMESTAMP - INTERVAL '6 days 2 hours', CURRENT_TIMESTAMP - INTERVAL '6 days 2 hours', 'seed', CURRENT_TIMESTAMP - INTERVAL '6 days 2 hours', 'seed'),
('22222222-2222-2222-2222-222222222222', 319.98, 0, 319.98, CURRENT_TIMESTAMP - INTERVAL '5 days 4 hours', CURRENT_TIMESTAMP - INTERVAL '5 days 4 hours', 'seed', CURRENT_TIMESTAMP - INTERVAL '5 days 4 hours', 'seed'),
('22222222-2222-2222-2222-222222222223', 164.97, 0, 164.97, CURRENT_TIMESTAMP - INTERVAL '5 days 1 hour', CURRENT_TIMESTAMP - INTERVAL '5 days 1 hour', 'seed', CURRENT_TIMESTAMP - INTERVAL '5 days 1 hour', 'seed'),
('22222222-2222-2222-2222-222222222224', 474.97, 0, 474.97, CURRENT_TIMESTAMP - INTERVAL '4 days 6 hours', CURRENT_TIMESTAMP - INTERVAL '4 days 6 hours', 'seed', CURRENT_TIMESTAMP - INTERVAL '4 days 6 hours', 'seed'),
('22222222-2222-2222-2222-222222222225', 319.97, 0, 319.97, CURRENT_TIMESTAMP - INTERVAL '4 days 30 minutes', CURRENT_TIMESTAMP - INTERVAL '4 days 30 minutes', 'seed', CURRENT_TIMESTAMP - INTERVAL '4 days 30 minutes', 'seed'),
('22222222-2222-2222-2222-222222222226', 519.97, 0, 519.97, CURRENT_TIMESTAMP - INTERVAL '3 days 8 hours', CURRENT_TIMESTAMP - INTERVAL '3 days 8 hours', 'seed', CURRENT_TIMESTAMP - INTERVAL '3 days 8 hours', 'seed'),
('22222222-2222-2222-2222-222222222227', 187.96, 0, 187.96, CURRENT_TIMESTAMP - INTERVAL '2 days 7 hours', CURRENT_TIMESTAMP - INTERVAL '2 days 7 hours', 'seed', CURRENT_TIMESTAMP - INTERVAL '2 days 7 hours', 'seed'),
('22222222-2222-2222-2222-222222222228', 589.98, 0, 589.98, CURRENT_TIMESTAMP - INTERVAL '2 days 2 hours', CURRENT_TIMESTAMP - INTERVAL '2 days 2 hours', 'seed', CURRENT_TIMESTAMP - INTERVAL '2 days 2 hours', 'seed'),
('22222222-2222-2222-2222-222222222229', 129.97, 0, 129.97, CURRENT_TIMESTAMP - INTERVAL '1 day 9 hours', CURRENT_TIMESTAMP - INTERVAL '1 day 9 hours', 'seed', CURRENT_TIMESTAMP - INTERVAL '1 day 9 hours', 'seed'),
('22222222-2222-2222-2222-222222222221', 179.97, 0, 179.97, CURRENT_TIMESTAMP - INTERVAL '20 hours', CURRENT_TIMESTAMP - INTERVAL '20 hours', 'seed', CURRENT_TIMESTAMP - INTERVAL '20 hours', 'seed'),
('22222222-2222-2222-2222-222222222223', 539.97, 0, 539.97, CURRENT_TIMESTAMP - INTERVAL '10 hours', CURRENT_TIMESTAMP - INTERVAL '10 hours', 'seed', CURRENT_TIMESTAMP - INTERVAL '10 hours', 'seed'),
('22222222-2222-2222-2222-222222222226', 174.97, 0, 174.97, CURRENT_TIMESTAMP - INTERVAL '2 hours', CURRENT_TIMESTAMP - INTERVAL '2 hours', 'seed', CURRENT_TIMESTAMP - INTERVAL '2 hours', 'seed');

-- ==============================================
-- Sale details
-- ==============================================
INSERT INTO sale_details (sale_id, product_id, product_name, brand_name, category_name, quantity, price, discount) VALUES
(1, 1, 'Air Zoom Pegasus 41', 'Nike', 'Running', 1, 139.99, 0),
(1, 28, 'Stainless Bottle 32oz', 'Hydro Flask', 'Accessories', 2, 39.99, 0),
(2, 6, 'Metcon 9', 'Nike', 'Training', 2, 149.99, 0),
(2, 27, 'Training Gloves', 'Reebok', 'Accessories', 1, 22.99, 0),
(3, 10, 'Training Shorts', 'Under Armour', 'Training', 3, 44.99, 0),
(3, 29, 'Performance Socks 3 Pack', 'New Balance', 'Accessories', 1, 17.99, 0),
(4, 21, 'Mercurial Vapor 16', 'Nike', 'Football', 1, 249.99, 0),
(4, 24, 'Match Football', 'Wilson', 'Football', 1, 34.99, 0),
(4, 25, 'Shin Guards Pro', 'Adidas', 'Football', 2, 24.99, 0),
(4, 26, 'Duffle Bag 40L', 'Nike', 'Accessories', 1, 54.99, 0),
(5, 11, 'Air Force 1 Low', 'Nike', 'Lifestyle', 2, 119.99, 0),
(5, 15, 'Essentials Hoodie', 'Adidas', 'Lifestyle', 1, 64.99, 0),
(5, 29, 'Performance Socks 3 Pack', 'New Balance', 'Accessories', 1, 17.99, 0),
(6, 31, 'Forerunner 265', 'Garmin', 'Electronics', 1, 449.99, 0),
(6, 35, 'Bike Speed Sensor', 'Garmin', 'Electronics', 1, 39.99, 0),
(6, 40, 'Electrolyte Tablets', 'Optimum Nutrition', 'Nutrition', 2, 14.99, 0),
(7, 18, 'Trail Jacket', 'New Balance', 'Outdoor', 1, 129.99, 0),
(7, 20, 'Thermal Base Layer', 'Under Armour', 'Outdoor', 1, 54.99, 0),
(7, 40, 'Electrolyte Tablets', 'Optimum Nutrition', 'Nutrition', 1, 14.99, 0),
(8, 22, 'Predator Elite', 'Adidas', 'Football', 2, 259.99, 0),
(8, 24, 'Match Football', 'Wilson', 'Football', 2, 34.99, 0),
(9, 30, 'Yoga Mat 6mm', 'Under Armour', 'Accessories', 3, 29.99, 0),
(9, 37, 'Amino Energy', 'Optimum Nutrition', 'Nutrition', 1, 24.99, 0),
(9, 38, 'Creatine Monohydrate', 'Optimum Nutrition', 'Nutrition', 1, 19.99, 0),
(10, 28, 'Stainless Bottle 32oz', 'Hydro Flask', 'Accessories', 1, 39.99, 0),
(10, 36, 'Gold Standard Whey 2lb', 'Optimum Nutrition', 'Nutrition', 1, 39.99, 0),
(10, 39, 'Protein Bar Box', 'Optimum Nutrition', 'Nutrition', 2, 29.99, 0),
(10, 40, 'Electrolyte Tablets', 'Optimum Nutrition', 'Nutrition', 1, 14.99, 0),
(10, 29, 'Performance Socks 3 Pack', 'New Balance', 'Accessories', 1, 17.99, 0),
(11, 32, 'Venu 3', 'Garmin', 'Electronics', 1, 399.99, 0),
(11, 33, 'Heart Rate Monitor', 'Garmin', 'Electronics', 1, 89.99, 0),
(11, 34, 'Wireless Earbuds Sport', 'Under Armour', 'Electronics', 1, 99.99, 0),
(11, 38, 'Creatine Monohydrate', 'Optimum Nutrition', 'Nutrition', 1, 19.99, 0),
(11, 40, 'Electrolyte Tablets', 'Optimum Nutrition', 'Nutrition', 2, 14.99, 0),
(12, 16, 'Speedcross 6', 'Salomon', 'Outdoor', 1, 144.99, 0),
(12, 40, 'Electrolyte Tablets', 'Optimum Nutrition', 'Nutrition', 2, 14.99, 0);
