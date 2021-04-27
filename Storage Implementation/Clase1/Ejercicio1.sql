CREATE DATABASE ml_app_consultorio CHARACTER SET utf8 COLLATE utf8_general_ci;
SHOW DATABASES;

USE ml_app_consultorio;

-- Se usa @'%' en lugar del @'localhost' para que funcione con docker. 
-- DROP USER 'ml_app_user1'@'%';
CREATE USER 'ml_app_user1'@'%' IDENTIFIED BY 'ml_app_user1';

SELECT * FROM mysql.user;

GRANT SELECT, INSERT, UPDATE, DELETE, CREATE
ON ml_app_consultorio.* 
TO 'ml_app_user1'@'%';

FLUSH PRIVILEGES;