CREATE TABLE IF NOT EXISTS selling_prices (
                                              id BIGSERIAL PRIMARY KEY,

                                              product_id BIGINT NOT NULL
                                              REFERENCES products(id),

    shop_id BIGINT NOT NULL
    REFERENCES shops(id),

    selling_price NUMERIC(12,2) NOT NULL
    CHECK (selling_price >= 0),

    active BOOLEAN NOT NULL DEFAULT TRUE,

    CONSTRAINT uk_selling_price_product_shop
    UNIQUE (product_id, shop_id)
    );

CREATE INDEX IF NOT EXISTS idx_selling_prices_product
    ON selling_prices(product_id);

CREATE INDEX IF NOT EXISTS idx_selling_prices_shop
    ON selling_prices(shop_id);