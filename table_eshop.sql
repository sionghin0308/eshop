CREATE TABLE product (
	product_id VARCHAR(100) NOT NULL PRIMARY KEY,
	product_name VARCHAR(100),
	product_quantity NUMERIC,
	product_price NUMERIC
);

CREATE SEQUENCE customer_id_seq;
CREATE TABLE customer (
	customer_id NUMERIC NOT NULL DEFAULT nextval('customer_id_seq') PRIMARY KEY,
	customer_name VARCHAR(100),
	customer_age NUMERIC,
	customer_username VARCHAR(100),
	customer_password VARCHAR(100)
);
ALTER SEQUENCE customer_id_seq
OWNED BY customer.customer_id;

CREATE SEQUENCE id_seq;
CREATE TABLE order_list (
	id NUMERIC NOT NULL DEFAULT nextval('id_seq') PRIMARY KEY,
	order_id VARCHAR(100) NOT NULL,
	product_id VARCHAR(100),
	customer_id NUMERIC,
	order_time TIMESTAMP,
	order_quantity NUMERIC,
	order_price NUMERIC,
	order_status VARCHAR(20),
	FOREIGN KEY (product_id)
      REFERENCES product (product_id),
    FOREIGN KEY (customer_id)
      REFERENCES customer (customer_id)
);

INSERT INTO product(product_id, product_name, product_quantity, product_price) VALUES ('APPLE0001', 'iPhone 12 Pro', 0, 1649);
INSERT INTO product(product_id, product_name, product_quantity, product_price) VALUES ('APPLE0002', 'iPhone 12 Pro Max', 10, 1799);

INSERT INTO customer(customer_name, customer_age) VALUES('Gan Siong Hin', 27);