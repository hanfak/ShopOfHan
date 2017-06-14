DROP DATABASE IF EXISTS shop_of_han_database;

CREATE DATABASE IF NOT EXISTS shop_of_han_database;

USE shop_of_han_database;

CREATE TABLE IF NOT EXISTS product
(
  id              INT unsigned NOT NULL AUTO_INCREMENT,
  product_name    VARCHAR(150) NOT NULL,
  product_id      VARCHAR(50) NOT NULL,
  description     VARCHAR(500) NOT NULL,
  selling_price   NUMBER(10,2) NOT NULL,
  PRIMARY KEY     (id)
);

INSERT INTO stock (product_name, product_id, description, selling_price) VALUES
('Joy Of java', 'JOJ-1', 'Book about java', 19.99),
('SQL the sequel', 'STS-1', 'Book about sql', 29.99),
('Unix', 'UNI-1', 'Book about unix', 0.99);

-- Add product id foreign key in here
CREATE TABLE IF NOT EXISTS stock
(
  id              INT unsigned NOT NULL AUTO_INCREMENT,
  product_name    VARCHAR(150) NOT NULL,
  amount          INT,
  PRIMARY KEY     (id)
);

INSERT INTO stock (product_name, amount) VALUES
('Joy Of java', 4),
('SQL the sequel', 0);

