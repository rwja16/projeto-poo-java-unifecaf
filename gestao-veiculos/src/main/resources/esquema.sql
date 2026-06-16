-- =====================================================================
-- SISTEMA DE GESTÃO DE ESTOQUE DE VEÍCULOS
-- Modelo Físico do Banco de Dados - MySQL
-- =====================================================================
-- Observação: o Hibernate cria as tabelas automaticamente
-- (ddl-auto=update). Este script serve como modelo físico de referência
-- e pode ser executado manualmente caso prefira criar o banco na mão.
-- =====================================================================

-- Cria o banco de dados se não existir
CREATE DATABASE IF NOT EXISTS gestao_veiculos
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE gestao_veiculos;

-- =====================================================================
-- TABELA: brands (Marcas)
-- =====================================================================
CREATE TABLE IF NOT EXISTS brands (
    id                 BIGINT AUTO_INCREMENT PRIMARY KEY,
    name               VARCHAR(100) NOT NULL UNIQUE,
    description        VARCHAR(255),
    country_of_origin  VARCHAR(100),
    created_at         DATETIME,
    updated_at         DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- =====================================================================
-- TABELA: vehicle_models (Modelos)
-- Relacionamento: cada modelo pertence a uma marca
-- =====================================================================
CREATE TABLE IF NOT EXISTS vehicle_models (
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(100) NOT NULL,
    category    VARCHAR(50),
    brand_id    BIGINT NOT NULL,
    created_at  DATETIME,
    updated_at  DATETIME,
    CONSTRAINT fk_model_brand
        FOREIGN KEY (brand_id) REFERENCES brands(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- =====================================================================
-- TABELA: vehicles (Veículos)
-- Relacionamento: cada veículo pertence a um modelo
-- =====================================================================
CREATE TABLE IF NOT EXISTS vehicles (
    id                 BIGINT AUTO_INCREMENT PRIMARY KEY,
    color              VARCHAR(50) NOT NULL,
    manufacture_year   INT NOT NULL,
    price              DECIMAL(12,2) NOT NULL,
    mileage            INT NOT NULL,
    status             VARCHAR(20) NOT NULL,
    notes              VARCHAR(500),
    vehicle_model_id   BIGINT NOT NULL,
    created_at         DATETIME,
    updated_at         DATETIME,
    CONSTRAINT fk_vehicle_model
        FOREIGN KEY (vehicle_model_id) REFERENCES vehicle_models(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- =====================================================================
-- ÍNDICES (melhoram a busca por filtros)
-- =====================================================================
CREATE INDEX idx_vehicle_status        ON vehicles(status);
CREATE INDEX idx_vehicle_year          ON vehicles(manufacture_year);
CREATE INDEX idx_vehicle_price         ON vehicles(price);
CREATE INDEX idx_vehicle_model         ON vehicles(vehicle_model_id);
CREATE INDEX idx_model_brand           ON vehicle_models(brand_id);
