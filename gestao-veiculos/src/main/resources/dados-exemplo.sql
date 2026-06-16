-- =====================================================================
-- DADOS DE EXEMPLO - Sistema de Gestão de Veículos
-- =====================================================================
-- Rode este script depois que as tabelas estiverem criadas, caso queira
-- popular o banco com alguns dados pra testar.
-- =====================================================================

USE gestao_veiculos;

-- ==================== MARCAS ====================
INSERT INTO brands (name, description, country_of_origin, created_at, updated_at) VALUES
('Toyota',     'Montadora japonesa conhecida pela durabilidade', 'Japão',  NOW(), NOW()),
('Volkswagen', 'Montadora alemã com forte presença no Brasil',  'Alemanha', NOW(), NOW()),
('Chevrolet',  'Marca americana da General Motors',             'EUA',    NOW(), NOW()),
('Honda',      'Montadora japonesa de carros e motos',          'Japão',  NOW(), NOW()),
('Fiat',       'Montadora italiana popular no Brasil',          'Itália', NOW(), NOW());

-- ==================== MODELOS ====================
INSERT INTO vehicle_models (name, category, brand_id, created_at, updated_at) VALUES
('Corolla',  'Sedan',  1, NOW(), NOW()),
('Hilux',    'Pickup', 1, NOW(), NOW()),
('Gol',      'Hatch',  2, NOW(), NOW()),
('T-Cross',  'SUV',    2, NOW(), NOW()),
('Onix',     'Hatch',  3, NOW(), NOW()),
('Tracker',  'SUV',    3, NOW(), NOW()),
('Civic',    'Sedan',  4, NOW(), NOW()),
('Argo',     'Hatch',  5, NOW(), NOW());

-- ==================== VEÍCULOS ====================
INSERT INTO vehicles (color, manufacture_year, price, mileage, status, notes, vehicle_model_id, created_at, updated_at) VALUES
('Prata',   2022, 125000.00, 30000, 'DISPONIVEL',  'Único dono, revisões em dia',     1, NOW(), NOW()),
('Branco',  2023, 145000.00, 15000, 'DISPONIVEL',  'Seminovo',                        1, NOW(), NOW()),
('Preto',   2021, 210000.00, 60000, 'DISPONIVEL',  'Diesel 4x4',                      2, NOW(), NOW()),
('Vermelho',2019,  55000.00, 80000, 'VENDIDO',     'Vendido em maio',                 3, NOW(), NOW()),
('Cinza',   2024, 160000.00,  5000, 'DISPONIVEL',  'Zero km praticamente',            4, NOW(), NOW()),
('Branco',  2023,  98000.00, 22000, 'RESERVADO',   'Reservado para cliente',          5, NOW(), NOW()),
('Azul',    2022, 135000.00, 28000, 'DISPONIVEL',  'Completo',                        6, NOW(), NOW()),
('Preto',   2020, 115000.00, 45000, 'DISPONIVEL',  'Automático',                      7, NOW(), NOW()),
('Branco',  2021,  72000.00, 38000, 'DISPONIVEL',  'Econômico',                       8, NOW(), NOW()),
('Prata',   2018,  48000.00, 95000, 'DESCONTINUADO','Saiu de linha',                  3, NOW(), NOW());
