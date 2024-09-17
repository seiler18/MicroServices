-- Elimina las tablas si ya existen
DROP TABLE IF EXISTS tbl_invoce_items;
DROP TABLE IF EXISTS tlb_invoices;
DROP TABLE IF EXISTS tbl_customers;

-- Crea la tabla tbl_customers
CREATE TABLE tbl_customers (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255)
);

-- Crea la tabla tlb_invoices
CREATE TABLE tlb_invoices (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    number_invoice VARCHAR(50) NOT NULL,
    description VARCHAR(255),
    customer_id BIGINT,
    create_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    state VARCHAR(50) NOT NULL,
    FOREIGN KEY (customer_id) REFERENCES tbl_customers(id)
);

-- Crea la tabla tbl_invoce_items
CREATE TABLE tbl_invoce_items (
    invoice_id BIGINT,
    product_id BIGINT,
    quantity INT NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    PRIMARY KEY (invoice_id, product_id),
    FOREIGN KEY (invoice_id) REFERENCES tlb_invoices(id)
    -- FOREIGN KEY (product_id) REFERENCES tbl_products(id) -- Si existe la tabla tbl_products, descomenta esta línea
);
