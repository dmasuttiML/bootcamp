/*
1. Explique el concepto de normalización y para que se utiliza.

La normalización es un proceso de estandarización y validación de datos 
que consiste en eliminar las redundancias o inconsistencias, completando 
datos mediante una serie de reglas que actualizan la información, protegiendo 
su integridad y favoreciendo la interpretación, para que así sea más fácil de 
consultar y más útil para quien la gestiona.
*/

/*
2. Agregue una película a la tabla movies.
*/

INSERT INTO movies (title, rating, awards, release_date, length, genre_id) 
VALUES ("Men in Black", 9.8, 2, '1997-07-17', 98, 5);

/*
3. Agregué un géneró a la tabla genres.
*/

INSERT INTO genres (created_at, name, ranking)  
VALUES (CURRENT_TIMESTAMP(), "Bélicas", 13);

/*
4. Asocie a la película del Ej 2. con el género creado en el Ej. 3.
*/

SELECT id FROM movies WHERE title like "Men in Black";

UPDATE movies 
SET genre_id = (SELECT id FROM genres WHERE name like "Bélicas") 
WHERE id = 22;

/*
5. Modifique la tabla actors para que al menos un actor tenga como
favorita la película agregada en el Ej.2.
*/

SELECT id FROM actors LIMIT 1;

UPDATE actors 
SET favorite_movie_id = (SELECT id FROM movies 
						 WHERE title like "Men in Black")
WHERE id = 3;

/*
6. Cree una tabla temporal copia de la tabla movies.
*/

CREATE TEMPORARY TABLE movies_temporal SELECT * FROM movies;

/*
7. Elimine de esa tabla temporal todas las películas que hayan ganado
menos de 5 awards.
*/

DELETE FROM movies_temporal
WHERE awards < 5;

/*
8. Obtenga la lista de todas los géneros que tengan al menos una
película.
*/

SELECT g.*, count(m.id) cant_movies
FROM genres g
JOIN movies m ON g.id = m.genre_id
GROUP BY g.id HAVING cant_movies > 0; 

/*
9. Obtenga la lista de actores cuya película favorita haya ganado más
de 3 awards.
*/

SELECT a.* 
FROM actors a
JOIN movies m ON a.favorite_movie_id = m.id
WHERE m.awards > 3; 

/*
10. Utilice el explain plan para analizar las consultas del Ej.6 y 7.
*/

DROP TABLE movies_temporal;
CREATE TEMPORARY TABLE movies_temporal SELECT * FROM movies;
EXPLAIN DELETE FROM movies_temporal WHERE awards < 5;

/*
11. Qué son los índices? Para qué sirven?
R: Es un mecanismo para optimizar consultas en SQL.Mejoran el acceso a los datos 
al proporcionar una ruta más directa a los datos almacenados para evitar realizar 
escaneos completos de los datos en una tabla.
*/

/*
12. Cree un índice sobre el nombre en la tabla movies.
*/

CREATE INDEX title_idx ON movies (title);

/*
13. Chequee que el indice fue creado correctamente.
*/

SHOW INDEX FROM movies;
