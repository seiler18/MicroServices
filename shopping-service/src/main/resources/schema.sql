-- Elimina las tablas si ya existen
DROP TABLE IF EXISTS tbl_invoce_items;
DROP TABLE IF EXISTS tbl_invoices;
DROP TABLE IF EXISTS tbl_customers;

-- Crea la tabla tbl_customers
CREATE TABLE tbl_customers (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255)
);

-- Crea la tabla tbl_invoices
CREATE TABLE tbl_invoices (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    number_invoice VARCHAR(255),
    description VARCHAR(255),
    customer_id BIGINT,
    create_at DATE,
    state VARCHAR(50)
);

-- Crea la tabla tbl_invoice_items
CREATE TABLE tbl_invoice_items (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,  -- Agregar esta columna como clave primaria
    invoice_id BIGINT,
    product_id BIGINT,
    quantity INT NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (invoice_id) REFERENCES tbl_invoices(id)
    -- FOREIGN KEY (product_id) REFERENCES tbl_products(id) -- Si existe la tabla tbl_products, descomenta esta línea
);
