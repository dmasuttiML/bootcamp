-- PRIMERA PARTE
/*
1. A qué se denomina JOIN en una base datos?
R: A una union entre dos tablas.
*/

/*
2. Nombre y explique 2 tipos de JOIN.
- LEFT JOIN: Devuelve todas las filas de la tabla de la izquierda, y las filas coincidentes de la tabla de la derecha.
- RIGHT JOIN: Devuelve todas las filas de la tabla de la derecha, y las filas coincidentes de la tabla de la izquierda.
*/

/*
3. Para qué se utiliza el GROUP BY?
R: Para agrupar filas que tienen el mismo valor en filas de resumen.
*/

/*
4. Para qué se utiliza el HAVING?
R: Para filtrar filas por campos resultantes de un agrupado. 
*/

/*
5. Dado lo siguientes diagramas indique a qué tipo de JOIN corresponde cada uno:
- Caso 1: INNER JOIN
- Caso 2: LEFT JOIN
*/

/*
6. Escriba una consulta genérica por cada uno de los diagramas a continuación:
- Caso1:
	SELECT * FROM TablaA
    RIGHT JOIN TablaB ON TablaA.id = TablaB.tablaa_id

- Caso2:
	SELECT * FROM TablaA
    LEFT JOIN TablaB ON TablaA.id = TablaB.tablaa_id
    UNION ALL
    SELECT * FROM TablaA
    RIGHT JOIN TablaB ON TablaA.id = TablaB.tablaa_id
*/

-- SEGUNDA PARTE
/*
1. Mostrar el título y el nombre del género de todas las series.
*/

SELECT s.title, g.name 
FROM series s
JOIN genres g ON s.genre_id = g.id; 

/*
2. Mostrar el título de los episodios, el nombre y apellido de los actores que 
trabajan en cada uno de ellos.
*/

SELECT e.title, a.first_name, a.last_name 
FROM episodes e
JOIN actor_episode ae ON e.id = ae.episode_id
JOIN actors a ON ae.actor_id = a.id;

/*
3. Mostrar el título de todas las series y el total de temporadas que tiene
cada una de ellas.
*/

SELECT DISTINCT s.title, count(se.id) cant_seasons 
FROM series s
JOIN seasons se ON s.id = se.serie_id
GROUP BY s.title;

/*
4. Mostrar el nombre de todos los géneros y la cantidad total de películas
por cada uno, siempre que sea mayor o igual a 3.
*/

SELECT g.name, count(m.id) cant_movies
FROM genres g
JOIN movies m ON g.id = m.genre_id
GROUP BY g.name HAVING cant_movies >= 3;

/*
5. Mostrar sólo el nombre y apellido de los actores que trabajan en todas las
películas de la guerra de las galaxias y que estos no se repitan.
*/

SELECT a.first_name, a.last_name 
FROM actors a
JOIN actor_movie am ON a.id = am.actor_id
JOIN movies m ON m.id = am.movie_id
WHERE m.title like "%guerra de las galaxias%"
GROUP BY a.first_name, a.last_name;
