/* Populate table clients */

INSERT INTO clients (id, name, surname, email, created_at) VALUES (1, 'David', 'Gómez', 'dagor@gmail.com', '2019-08-06');
INSERT INTO clients (id, name, surname, email, created_at) VALUES (2, 'Lucía', 'Astray', 'lulas@gmail.com', '2019-08-04');
INSERT INTO clients (id, name, surname, email, created_at) VALUES (3, 'Alfonso', 'Cerezuela', 'fons@gmail.com', '2019-07-15');
INSERT INTO clients (id, name, surname, email, created_at) VALUES (4, 'Enrique', 'Álvarez de Toledo', 'henry@gmail.com', '2019-07-15');
INSERT INTO clients (id, name, surname, email, created_at) VALUES (5, 'José Luis', 'Avilés', 'peplu@gmail.com', '2019-07-15');
INSERT INTO clients (id, name, surname, email, created_at) VALUES (6, 'Carlos', 'Martínez', 'carlos@gmail.com', '2015-05-04');
INSERT INTO clients (id, name, surname, email, created_at) VALUES (7, 'John', 'Doe', 'john.doe@gmail.com', '2017-08-02');
INSERT INTO clients (id, name, surname, email, created_at) VALUES (8, 'Linus', 'Torvalds', 'linus.torvalds@gmail.com', '2017-08-03');
INSERT INTO clients (id, name, surname, email, created_at) VALUES (9, 'Jane', 'Doe', 'jane.doe@gmail.com', '2017-08-04');
INSERT INTO clients (id, name, surname, email, created_at) VALUES (10, 'Rasmus', 'Lerdorf', 'rasmus.lerdorf@gmail.com', '2017-08-05');
INSERT INTO clients (id, name, surname, email, created_at) VALUES (11, 'Erich', 'Gamma', 'erich.gamma@gmail.com', '2017-08-06');
INSERT INTO clients (id, name, surname, email, created_at) VALUES (12, 'Richard', 'Helm', 'richard.helm@gmail.com', '2017-08-07');
INSERT INTO clients (id, name, surname, email, created_at) VALUES (13, 'Ralph', 'Johnson', 'ralph.johnson@gmail.com', '2017-08-08');
INSERT INTO clients (id, name, surname, email, created_at) VALUES (14, 'John', 'Vlissides', 'john.vlissides@gmail.com', '2017-08-09');
INSERT INTO clients (id, name, surname, email, created_at) VALUES (15, 'James', 'Gosling', 'james.gosling@gmail.com', '2017-08-010');
INSERT INTO clients (id, name, surname, email, created_at) VALUES (16, 'Bruce', 'Lee', 'bruce.lee@gmail.com', '2017-08-11');
INSERT INTO clients (id, name, surname, email, created_at) VALUES (17, 'Johnny', 'Doe', 'johnny.doe@gmail.com', '2017-08-12');
INSERT INTO clients (id, name, surname, email, created_at) VALUES (18, 'John', 'Roe', 'john.roe@gmail.com', '2017-08-13');
INSERT INTO clients (id, name, surname, email, created_at) VALUES (19, 'Jane', 'Roe', 'jane.roe@gmail.com', '2017-08-14');
INSERT INTO clients (id, name, surname, email, created_at) VALUES (20, 'Richard', 'Doe', 'richard.doe@gmail.com', '2017-08-15');

/* Populate table products */

INSERT INTO products (name, price, discontinued, created_at) VALUES ('Panasonic Pantalla LCD', 259990, false, NOW());
INSERT INTO products (name, price, discontinued, created_at) VALUES ('Sony Camara digital DSC-W320B', 123490, false, NOW());
INSERT INTO products (name, price, discontinued, created_at) VALUES ('Apple iPod shuffle', 1499990, true, NOW());
INSERT INTO products (name, price, discontinued, created_at) VALUES ('Sony Notebook Z110', 37990, false, NOW());
INSERT INTO products (name, price, discontinued, created_at) VALUES ('Hewlett Packard Multifuncional F2280', 69990, false, NOW());
INSERT INTO products (name, price, discontinued, created_at) VALUES ('Bianchi Bicicleta Aro 26', 69990, false, NOW());
INSERT INTO products (name, price, discontinued, created_at) VALUES ('Mica Comoda 5 Cajones', 299990, false, NOW());

/* Create some invoices */

INSERT INTO invoices (description, details, client_id, created_at) VALUES ('Factura equipos de oficina', null, 1, NOW());
INSERT INTO invoice_items (amount, invoice_id, product_id) VALUES (1, 1, 1);
INSERT INTO invoice_items (amount, invoice_id, product_id) VALUES (2, 1, 4);
INSERT INTO invoice_items (amount, invoice_id, product_id) VALUES (1, 1, 5);
INSERT INTO invoice_items (amount, invoice_id, product_id) VALUES (1, 1, 7);

INSERT INTO invoices (description, details, client_id, created_at) VALUES ('Factura Bicicleta', 'Alguna nota importante!', 1, NOW());
INSERT INTO invoice_items (amount, invoice_id, product_id) VALUES (3, 2, 6);

/* Create some users and authorities */

INSERT INTO users (username, password, enabled, image) VALUES ('admin', '$2a$10$O0TVkNh3TKKG01EYaOtSFu.TOUicVqmEPvtUtShNqUh/cL9KqBQ/G', 1, "https://res.cloudinary.com/ikeiala/image/upload/v1568284998/user.png");
INSERT INTO users (username, password, enabled, image) VALUES ('user', '$2a$10$t/9qmzu5hWggiia9pxQHE.HAvElxU.lOkG3nFEsJ48QfTA5Z2ePm6', 1, "https://res.cloudinary.com/ikeiala/image/upload/v1568284998/user.png");

INSERT INTO authorities (user_id, authority) VALUES (1, 'ROLE_USER');
INSERT INTO authorities (user_id, authority) VALUES (1, 'ROLE_ADMIN');
INSERT INTO authorities (user_id, authority) VALUES (2, 'ROLE_USER');