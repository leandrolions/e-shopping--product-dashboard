DROP TABLE IF EXISTS Product;
 
CREATE TABLE Product (
  id BIGINT PRIMARY KEY,
  name VARCHAR(250) NOT NULL,
  category VARCHAR(250) NOT NULL,
  retailPrice DOUBLE NOT NULL,
  discountedPrice DOUBLE ,
  availability	BOOLEAN ,
  discountPercentage INT
);