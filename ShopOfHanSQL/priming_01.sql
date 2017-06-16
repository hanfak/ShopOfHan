DROP DATABASE IF EXISTS shop_of_han_database;

CREATE DATABASE IF NOT EXISTS shop_of_han_database;

USE shop_of_han_database;

CREATE TABLE IF NOT EXISTS stock
(
  id              INT unsigned NOT NULL AUTO_INCREMENT,
  product_name    VARCHAR(150) NOT NULL,
  amount          INT,
  PRIMARY KEY     (id)
);

INSERT INTO stock (product_name, amount) VALUES
('Joy Of Java', 4),
('SQL the sequel', 0);
