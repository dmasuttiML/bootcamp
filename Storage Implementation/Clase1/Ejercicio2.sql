USE ml_app_consultorio;

-- Se usa @'%' en lugar del @'localhost' para que funcione con docker. 
-- DROP USER 'ml_app_user2'@'%';
CREATE USER 'ml_app_user2'@'%' IDENTIFIED BY 'ml_app_user2';

SELECT * FROM mysql.user;

GRANT ALL PRIVILEGES
ON ml_app_consultorio.* 
TO 'ml_app_user2'@'%';

FLUSH PRIVILEGES;