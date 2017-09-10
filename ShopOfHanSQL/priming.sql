DROP DATABASE IF EXISTS shop_of_han_database;

CREATE DATABASE IF NOT EXISTS shop_of_han_database;

USE shop_of_han_database;

CREATE TABLE product
(
  id                    INT AUTO_INCREMENT,
  product_name          VARCHAR(150) NOT NULL,
  product_id            VARCHAR(20) NOT NULL,
  product_description   VARCHAR(250) NOT NULL,
  PRIMARY KEY           (id)
) ENGINE=INNODB;

CREATE TABLE stock
(
  id                INT AUTO_INCREMENT,
  product_id        INT,
  INDEX product_id_x (product_id),
  stock_id          VARCHAR(20) NOT NULL,
  stock_description VARCHAR(250) NOT NULL,
  amount            INT,
  PRIMARY KEY       (id),
  FOREIGN KEY       (product_id) REFERENCES product(id)
) ENGINE=INNODB;

INSERT INTO product (product_name, product_description, product_id) VALUES
('Joy Of Java','', 'JOJ1'),
('SQL the sequel','', 'STS1');

INSERT INTO stock (product_id, stock_id, stock_description, amount) VALUES
(1, 'STD1', '', 4),
(2, 'STD1', '', 0),
(2, 'STD2', '', 3);

SELECT * FROM product WHERE id IN (SELECT product_id FROM stock);
SELECT * FROM stock WHERE id IN (SELECT product_id FROM stock);

SELECT product.product_name, stock.amount FROM product INNER JOIN stock ON stock.product_id = product.id WHERE product_name='Joy Of Java';
SELECT product.product_name, stock.amount, stock.stock_id FROM product INNER JOIN stock ON stock.product_id = product.id WHERE product.product_id='STS1';
SELECT product.product_name, product.product_id, stock.amount, stock.stock_id FROM product INNER JOIN stock ON stock.product_id = product.id WHERE product.product_id='STS1';