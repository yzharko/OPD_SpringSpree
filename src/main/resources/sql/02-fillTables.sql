INSERT INTO mythology (name) VALUES
('Greek'),
('Norse'),
('Egyptian');

INSERT INTO category (name, hazard, rarity) VALUES
('Potion', 'Low', 'Common'),
('Weapon', 'High', 'Rare'),
('Artifact', 'Medium', 'Uncommon');

INSERT INTO city (name, delivery_time) VALUES
('New York', 2353263),
('London', 30000),
('Tokyo', 234553);

INSERT INTO step (name, description) VALUES
('Buy Placed', 'Customer submitted the buy'),
('Payment Received', 'Payment has been confirmed'),
('Sent', 'Gargoyle has been sent'),
('Delivered', 'Buy has been delivered');

INSERT INTO customer (city_id, name, email) VALUES
((SELECT id FROM city WHERE name = 'New York'), 'John Doe', 'john.doe@example.com'),
((SELECT id FROM city WHERE name = 'London'), 'Jane Smith', 'jane.smith@example.com'),
((SELECT id FROM city WHERE name = 'Tokyo'), 'Taro Tanaka', 'taro.tanaka@example.jp');

INSERT INTO buy_step (step_id, date_start, date_end) VALUES
((SELECT id FROM step WHERE name = 'Buy Placed'), '2023-10-26', '2023-10-26'),
((SELECT id FROM step WHERE name = 'Payment Received'), '2023-10-26', '2023-10-26'),
((SELECT id FROM step WHERE name = 'Sent'), '2023-10-27', '2024-01-02');

INSERT INTO product (category_id, mythology_id, name, price, description, pic) VALUES
((SELECT id FROM category WHERE name = 'Potion'), (SELECT id FROM mythology WHERE name = 'Greek'), 'Healing Potion', 10.00, 'Restores 50 HP', 'healing_potion.jpg'),
((SELECT id FROM category WHERE name = 'Weapon'), (SELECT id FROM mythology WHERE name = 'Norse'), 'Thor Hammer', 1000.00, 'Legendary hammer', 'thors_hammer.jpg'),
((SELECT id FROM category WHERE name = 'Artifact'), (SELECT id FROM mythology WHERE name = 'Egyptian'), 'Amulet of Anubis', 500.00, 'Protects from evil', 'anubis_amulet.jpg');

INSERT INTO buy (buy_step_id, customer_id, description) VALUES
((SELECT id FROM buy_step WHERE step_id = (SELECT id FROM step WHERE name = 'Buy Placed') AND date_start = '2023-10-26'), (SELECT id FROM customer WHERE name = 'John Doe'), 'Online Buy');

INSERT INTO buy_product (product_id, buy_id, amount) VALUES
((SELECT id FROM product WHERE name = 'Healing Potion'), (SELECT id FROM buy WHERE description = 'Online Buy'), 2),
((SELECT id FROM product WHERE name = 'Thor Hammer'), (SELECT id FROM buy WHERE description = 'Online Buy'), 1);
