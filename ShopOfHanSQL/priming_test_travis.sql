USE shop_of_han_database_test;

CREATE TABLE product
(
  id                    INT AUTO_INCREMENT,
  product_name          VARCHAR(150) NOT NULL,
  product_id            VARCHAR(20) NOT NULL UNIQUE,
  product_description   VARCHAR(250) NOT NULL,
  PRIMARY KEY           (id)
) ENGINE=INNODB;

CREATE TABLE stock
(
  id                INT AUTO_INCREMENT,
  product_id        VARCHAR(20),
  INDEX product_id_x (product_id),
  stock_id          VARCHAR(20) NOT NULL,
  stock_description VARCHAR(250) NOT NULL,
  amount            INT,
  PRIMARY KEY       (id),
  CONSTRAINT products_product_id_fk FOREIGN KEY (product_id) REFERENCES product(product_id)
) ENGINE=INNODB;
