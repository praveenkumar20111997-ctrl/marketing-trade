-- Development seed data. Remove this migration before production if you do not want sample data.
INSERT INTO products (product_name, brand, active)
SELECT 'A4 Paper', 'TNPL', TRUE
WHERE NOT EXISTS (SELECT 1 FROM products WHERE product_name='A4 Paper');

INSERT INTO product_types (product_id, type_name, specification, unit, active)
SELECT p.id, 'TNPL', '70 GSM', 'REAM', TRUE FROM products p
WHERE p.product_name='A4 Paper' AND NOT EXISTS (SELECT 1 FROM product_types pt WHERE pt.product_id=p.id AND pt.type_name='TNPL' AND pt.specification='70 GSM');

INSERT INTO product_types (product_id, type_name, specification, unit, active)
SELECT p.id, 'TNPL', '80 GSM', 'REAM', TRUE FROM products p
WHERE p.product_name='A4 Paper' AND NOT EXISTS (SELECT 1 FROM product_types pt WHERE pt.product_id=p.id AND pt.type_name='TNPL' AND pt.specification='80 GSM');

INSERT INTO product_types (product_id, type_name, specification, unit, active)
SELECT p.id, 'B2B', '70 GSM', 'REAM', TRUE FROM products p
WHERE p.product_name='A4 Paper' AND NOT EXISTS (SELECT 1 FROM product_types pt WHERE pt.product_id=p.id AND pt.type_name='B2B' AND pt.specification='70 GSM');
