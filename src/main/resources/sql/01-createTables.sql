CREATE TABLE mythology (
    id SERIAL PRIMARY KEY,
	name VARCHAR(50)
);

CREATE TABLE category (
    id SERIAL PRIMARY KEY,
	name VARCHAR(50),
	hazard VARCHAR(50),
	rarity VARCHAR(50)
);

CREATE TABLE city (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
	delivery_time INT
);

CREATE TABLE step (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
	description VARCHAR(100)
);

CREATE TABLE product (
    id SERIAL PRIMARY KEY,
    category_id INT NOT NULL,
    mythology_id INT NOT NULL,
	name VARCHAR(50),
    price DECIMAL(8, 2),
	description VARCHAR(100),
	pic VARCHAR(150),
    FOREIGN KEY (category_id) REFERENCES category (id) ON DELETE CASCADE,
    FOREIGN KEY (mythology_id) REFERENCES mythology (id) ON DELETE CASCADE
);

CREATE TABLE customer (
    id SERIAL PRIMARY KEY,
	city_id INT NOT NULL,
	name VARCHAR(50) NOT NULL,
    email VARCHAR(50),
    FOREIGN KEY (city_id) REFERENCES city (id)
);

CREATE TABLE buy_step (
    id SERIAL PRIMARY KEY,
    step_id INT NOT NULL,
	date_start DATE,
	date_end DATE,
    FOREIGN KEY (step_id) REFERENCES step (id)
);

CREATE TABLE buy (
    id SERIAL PRIMARY KEY,
	buy_step_id INT NOT NULL,
	customer_id INT NOT NULL,
    description VARCHAR(100),
    FOREIGN KEY (customer_id) REFERENCES customer (id),
    FOREIGN KEY (buy_step_id) REFERENCES buy_step (id)
);

CREATE TABLE buy_product (
    id SERIAL PRIMARY KEY,
    product_id INT NOT NULL,
    buy_id INT NOT NULL,
	amount INT,
	FOREIGN KEY (product_id) REFERENCES product (id),
	FOREIGN KEY (buy_id) REFERENCES buy (id)
);
