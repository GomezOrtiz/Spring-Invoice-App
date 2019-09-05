/* Populate table clients */

INSERT INTO clients (name, surname, email, created_at) VALUES ('David', 'Gómez', 'dagor@gmail.com', '2019-08-06');
INSERT INTO clients (name, surname, email, created_at) VALUES ('Lucía', 'Astray', 'lulas@gmail.com', '2019-08-04');
INSERT INTO clients (name, surname, email, created_at) VALUES ('Alfonso', 'Cerezuela', 'fons@gmail.com', '2019-07-15');
INSERT INTO clients (name, surname, email, created_at) VALUES ('Enrique', 'Álvarez de Toledo', 'henry@gmail.com', '2019-07-15');
INSERT INTO clients (name, surname, email, created_at) VALUES ('José Luis', 'Avilés', 'peplu@gmail.com', '2019-07-15');
INSERT INTO clients (name, surname, email, created_at) VALUES ('Carlos', 'Martínez', 'carlos@gmail.com', '2015-05-04');
INSERT INTO clients (name, surname, email, created_at) VALUES ('John', 'Doe', 'john.doe@gmail.com', '2017-08-02');
INSERT INTO clients (name, surname, email, created_at) VALUES ('Linus', 'Torvalds', 'linus.torvalds@gmail.com', '2017-08-03');
INSERT INTO clients (name, surname, email, created_at) VALUES ('Jane', 'Doe', 'jane.doe@gmail.com', '2017-08-04');
INSERT INTO clients (name, surname, email, created_at) VALUES ('Rasmus', 'Lerdorf', 'rasmus.lerdorf@gmail.com', '2017-08-05');
INSERT INTO clients (name, surname, email, created_at) VALUES ('Erich', 'Gamma', 'erich.gamma@gmail.com', '2017-08-06');
INSERT INTO clients (name, surname, email, created_at) VALUES ('Richard', 'Helm', 'richard.helm@gmail.com', '2017-08-07');
INSERT INTO clients (name, surname, email, created_at) VALUES ('Ralph', 'Johnson', 'ralph.johnson@gmail.com', '2017-08-08');
INSERT INTO clients (name, surname, email, created_at) VALUES ('John', 'Vlissides', 'john.vlissides@gmail.com', '2017-08-09');
INSERT INTO clients (name, surname, email, created_at) VALUES ('James', 'Gosling', 'james.gosling@gmail.com', '2017-08-010');
INSERT INTO clients (name, surname, email, created_at) VALUES ('Bruce', 'Lee', 'bruce.lee@gmail.com', '2017-08-11');
INSERT INTO clients (name, surname, email, created_at) VALUES ('Johnny', 'Doe', 'johnny.doe@gmail.com', '2017-08-12');
INSERT INTO clients (name, surname, email, created_at) VALUES ('John', 'Roe', 'john.roe@gmail.com', '2017-08-13');
INSERT INTO clients (name, surname, email, created_at) VALUES ('Jane', 'Roe', 'jane.roe@gmail.com', '2017-08-14');
INSERT INTO clients (name, surname, email, created_at) VALUES ('Richard', 'Doe', 'richard.doe@gmail.com', '2017-08-15');
INSERT INTO clients (name, surname, email, created_at) VALUES ('Janie', 'Doe', 'janie.doe@gmail.com', '2017-08-16');
INSERT INTO clients (name, surname, email, created_at) VALUES ('Phillip', 'Webb', 'phillip.webb@gmail.com', '2017-08-17');
INSERT INTO clients (name, surname, email, created_at) VALUES ('Stephane', 'Nicoll', 'stephane.nicoll@gmail.com', '2017-08-18');
INSERT INTO clients (name, surname, email, created_at) VALUES ('Sam', 'Brannen', 'sam.brannen@gmail.com', '2017-08-19');  
INSERT INTO clients (name, surname, email, created_at) VALUES ('Juergen', 'Hoeller', 'juergen.Hoeller@gmail.com', '2017-08-20'); 
INSERT INTO clients (name, surname, email, created_at) VALUES ('Janie', 'Roe', 'janie.roe@gmail.com', '2017-08-21');
INSERT INTO clients (name, surname, email, created_at) VALUES ('John', 'Smith', 'john.smith@gmail.com', '2017-08-22');
INSERT INTO clients (name, surname, email, created_at) VALUES ('Joe', 'Bloggs', 'joe.bloggs@gmail.com', '2017-08-23');
INSERT INTO clients (name, surname, email, created_at) VALUES ('John', 'Stiles', 'john.stiles@gmail.com', '2017-08-24');
INSERT INTO clients (name, surname, email, created_at) VALUES ('Richard', 'Roe', 'stiles.roe@gmail.com', '2017-08-25');

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