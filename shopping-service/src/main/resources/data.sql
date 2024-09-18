-- Insertar una factura
INSERT INTO tbl_invoices (id, number_invoice, description, customer_id, create_at, state)
VALUES (1, '0001', 'invoice office items', 1, NOW(), 'CREATED');

-- Insertar items de la factura
INSERT INTO tbl_invoice_items (invoice_id, quantity, price, product_id)
VALUES (1, 2, 50.0, 1);  -- Asegúrate de que el valor 1 en invoice_id exista en tbl_invoices
