-- Elimina las tablas si ya existen para evitar conflictos
DROP TABLE IF EXISTS tbl_customers;
DROP TABLE IF EXISTS tbl_regions;

-- Crea la tabla tbl_regions primero
CREATE TABLE tbl_regions (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);

-- Ahora, crea la tabla tbl_customers
CREATE TABLE tbl_customers (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    number_id VARCHAR(50) NOT NULL,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    email VARCHAR(255) NOT NULL,
    photo_url VARCHAR(255),
    region_id BIGINT,
    state VARCHAR(50) NOT NULL,
    FOREIGN KEY (region_id) REFERENCES tbl_regions(id)
);
