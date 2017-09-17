drop table stock

drop table product

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
  product_id        INT,
  INDEX product_id_x (product_id),
  stock_id          VARCHAR(20) NOT NULL,
  stock_description VARCHAR(250) NOT NULL,
  amount            INT,
  PRIMARY KEY       (id),
  FOREIGN KEY       (product_id) REFERENCES product(id)
) ENGINE=INNODB;

INSERT INTO product (product_name, product_description, product_id) VALUES ('Joy Of Java','Book about java', 'JOJ1'), ('SQL the sequel','Book about SQL', 'STS1'), ('Scala the mountian','Book about Scala', 'SCM1')

INSERT INTO stock (product_id, stock_id, stock_description, amount) VALUES (1, 'STD1', 'Single Pack', 4), (2, 'STD1', 'Single Pack', 0), (2, 'STD2', 'Multi Pack', 3)