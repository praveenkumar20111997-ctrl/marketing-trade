CREATE TABLE users (
 id BIGSERIAL PRIMARY KEY,
 name VARCHAR(100) NOT NULL,
 email VARCHAR(150) NOT NULL UNIQUE,
 contact VARCHAR(30),
 password_hash VARCHAR(255) NOT NULL,
 role VARCHAR(20) NOT NULL CHECK (role IN ('ADMIN','USER')),
 active BOOLEAN NOT NULL DEFAULT TRUE,
 created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
 updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE shops (
 id BIGSERIAL PRIMARY KEY,
 shop_name VARCHAR(150) NOT NULL,
 owner_name VARCHAR(100),
 contact_number VARCHAR(30),
 address TEXT,
 location VARCHAR(150),
 active BOOLEAN NOT NULL DEFAULT TRUE,
 created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
 updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE products (
 id BIGSERIAL PRIMARY KEY,
 product_name VARCHAR(150) NOT NULL,
 brand VARCHAR(100),
 active BOOLEAN NOT NULL DEFAULT TRUE,
 created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE product_types (
 id BIGSERIAL PRIMARY KEY,
 product_id BIGINT NOT NULL REFERENCES products(id),
 type_name VARCHAR(100) NOT NULL,
 specification VARCHAR(100),
 unit VARCHAR(30) NOT NULL DEFAULT 'REAM',
 active BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE suppliers (
 id BIGSERIAL PRIMARY KEY,
 supplier_name VARCHAR(150) NOT NULL,
 contact VARCHAR(30),
 location VARCHAR(150),
 address TEXT,
 active BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE shop_product_prices (
 id BIGSERIAL PRIMARY KEY,
 shop_id BIGINT NOT NULL REFERENCES shops(id),
 product_type_id BIGINT NOT NULL REFERENCES product_types(id),
 selling_price NUMERIC(12,2) NOT NULL,
 effective_from DATE NOT NULL,
 effective_to DATE,
 active BOOLEAN NOT NULL DEFAULT TRUE,
 UNIQUE(shop_id, product_type_id, effective_from)
);

CREATE TABLE purchases (
 id BIGSERIAL PRIMARY KEY,
 supplier_id BIGINT REFERENCES suppliers(id),
 purchase_date DATE NOT NULL,
 invoice_number VARCHAR(100),
 notes TEXT,
 created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE purchase_items (
 id BIGSERIAL PRIMARY KEY,
 purchase_id BIGINT NOT NULL REFERENCES purchases(id) ON DELETE CASCADE,
 product_type_id BIGINT NOT NULL REFERENCES product_types(id),
 quantity NUMERIC(12,2) NOT NULL CHECK(quantity > 0),
 unit_cost NUMERIC(12,2) NOT NULL CHECK(unit_cost >= 0),
 total_cost NUMERIC(14,2) NOT NULL
);

CREATE TABLE delivery_trips (
 id BIGSERIAL PRIMARY KEY,
 trip_date DATE NOT NULL,
 vehicle_type VARCHAR(50),
 vehicle_rent NUMERIC(12,2) NOT NULL DEFAULT 0,
 fuel_cost NUMERIC(12,2) NOT NULL DEFAULT 0,
 notes TEXT,
 created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE deliveries (
 id BIGSERIAL PRIMARY KEY,
 delivery_trip_id BIGINT REFERENCES delivery_trips(id),
 shop_id BIGINT NOT NULL REFERENCES shops(id),
 delivery_date DATE NOT NULL,
 status VARCHAR(20) NOT NULL DEFAULT 'DELIVERED',
 invoice_number VARCHAR(100),
 notes TEXT,
 created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE delivery_items (
 id BIGSERIAL PRIMARY KEY,
 delivery_id BIGINT NOT NULL REFERENCES deliveries(id) ON DELETE CASCADE,
 product_type_id BIGINT NOT NULL REFERENCES product_types(id),
 quantity NUMERIC(12,2) NOT NULL CHECK(quantity > 0),
 selling_price NUMERIC(12,2) NOT NULL,
 purchase_cost NUMERIC(12,2) NOT NULL,
 total_sales NUMERIC(14,2) NOT NULL,
 total_cost NUMERIC(14,2) NOT NULL,
 profit NUMERIC(14,2) NOT NULL
);

CREATE TABLE payments (
 id BIGSERIAL PRIMARY KEY,
 shop_id BIGINT NOT NULL REFERENCES shops(id),
 delivery_id BIGINT REFERENCES deliveries(id),
 payment_date DATE NOT NULL,
 amount NUMERIC(14,2) NOT NULL CHECK(amount > 0),
 payment_mode VARCHAR(20) NOT NULL,
 reference_number VARCHAR(100),
 notes TEXT,
 created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE expenses (
 id BIGSERIAL PRIMARY KEY,
 expense_date DATE NOT NULL,
 expense_type VARCHAR(50) NOT NULL,
 amount NUMERIC(14,2) NOT NULL CHECK(amount >= 0),
 description TEXT,
 delivery_trip_id BIGINT REFERENCES delivery_trips(id),
 created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE inventory_transactions (
 id BIGSERIAL PRIMARY KEY,
 product_type_id BIGINT NOT NULL REFERENCES product_types(id),
 transaction_type VARCHAR(30) NOT NULL,
 quantity NUMERIC(12,2) NOT NULL,
 reference_id BIGINT,
 transaction_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
 notes TEXT
);
