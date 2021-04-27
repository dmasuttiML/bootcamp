USE ml_app_consultorio;

-- Se usa @'%' en lugar del @'localhost' para que funcione con docker. 
-- DROP USER 'ml_app_user1'@'%';
CREATE USER 'ml_app_user3'@'%' IDENTIFIED BY 'ml_app_user3';

SELECT * FROM mysql.user;

GRANT SELECT
ON ml_app_consultorio.* 
TO 'ml_app_user3'@'%';

FLUSH PRIVILEGES;