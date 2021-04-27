CREATE SCHEMA `prueba_jpa` ;
SHOW DATABASES;

USE prueba_jpa;

-- DROP USER 'diego'@'%';
CREATE USER 'diego'@'%' IDENTIFIED BY '123456';

SELECT * FROM mysql.user;

-- GRANT SELECT, INSERT, UPDATE, DELETE, CREATE, DROP ON prueba_jpa.* TO 'diego'@'%';

GRANT ALL PRIVILEGES ON * . * TO 'diego'@'%';
SET SQL_SAFE_UPDATES = 0;
-- UPDATE mysql.user SET Grant_priv='Y', Super_priv='Y' WHERE user='diego';
FLUSH PRIVILEGES;

SHOW GRANTS FOR 'diego'@'%';