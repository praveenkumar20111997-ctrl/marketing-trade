CREATE INDEX idx_product_types_product ON product_types(product_id);
CREATE INDEX idx_purchase_date ON purchases(purchase_date);
CREATE INDEX idx_purchase_items_product_type ON purchase_items(product_type_id);
CREATE INDEX idx_inventory_product_type_date ON inventory_transactions(product_type_id, transaction_date);
CREATE INDEX idx_inventory_reference ON inventory_transactions(reference_id);
