DROP DATABASE IF EXISTS PreguntasBD;
CREATE DATABASE PreguntasBD;
USE PreguntasBD;

CREATE TABLE preguntas(
	id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    cadena VARCHAR(255) UNIQUE NOT NULL
);

CREATE TABLE respuestas(
	id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    cadena VARCHAR(255) UNIQUE NOT NULL
);

CREATE TABLE preguntas_respuestas(
	id_pregunta INT UNSIGNED NOT NULL,
    id_respuesta INT UNSIGNED NOT NULL,
    CONSTRAINT PK_p_r PRIMARY KEY (id_pregunta, id_respuesta),
    FOREIGN KEY (id_pregunta) REFERENCES preguntas(id) ON DELETE CASCADE ON UPDATE CASCADE,
    INDEX FK_PREGUNTA (id_pregunta), /*El INDEX se crea automáticamente en las FK pero compensa definirlo para elegir el nombre (es INDEX porue la relación es n:n, si fuera 1:n prodría ser UNIQUE)*/
    FOREIGN KEY (id_respuesta) REFERENCES respuestas(id) ON DELETE CASCADE ON UPDATE CASCADE,
    INDEX FK_RESPUESTA (id_respuesta)
);

/* No hacen falta porque UNIQUE ya crea un índice
CREATE INDEX idx_cadena_pregunta ON preguntas (cadena);
CREATE INDEX idx_cadena_respuesta ON respuestas (cadena);
*/

/*Triggers, Functions y Procedures*/
SET @using_insert_procedure = FALSE;
SET @using_delete_trigger = FALSE;

DELIMITER $$

CREATE TRIGGER block_preguntas_insert BEFORE INSERT ON preguntas
	FOR EACH ROW
		BEGIN
			IF NOT @using_insert_procedure THEN
				SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'No se puede insertar una pregunta sin respuesta';
			END IF;
		END $$

CREATE TRIGGER block_respuestas_insert BEFORE INSERT ON respuestas
	FOR EACH ROW
		BEGIN
			IF NOT @using_insert_procedure THEN
				SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'No se puede insertar una respuesta sin pregunta';
			END IF;
		END $$
        
CREATE TRIGGER delete_orphan_preguntas AFTER DELETE ON preguntas
	FOR EACH ROW
		BEGIN
			IF NOT @using_delete_trigger THEN	/*Para evitar que el trigger se dispare si es llamada por el otro trigger de borrado*/
        
				SET @using_delete_trigger = TRUE;
				DELETE respuestas FROM respuestas
					LEFT JOIN preguntas_respuestas ON preguntas_respuestas.id_respuesta = respuestas.id
						WHERE preguntas_respuestas.id_pregunta IS NULL;
				SET @using_delete_trigger = FALSE;
                
             END IF;
        END $$
        
CREATE TRIGGER delete_orphan_respuestas AFTER DELETE ON respuestas
	FOR EACH ROW
		BEGIN
			IF NOT @using_delete_trigger THEN	/*Para evitar que el trigger se dispare si es llamada por el otro trigger de borrado*/
            
				SET @using_delete_trigger = TRUE;
				DELETE preguntas FROM preguntas
					LEFT JOIN preguntas_respuestas ON preguntas_respuestas.id_pregunta = preguntas.id
						WHERE preguntas_respuestas.id_respuesta IS NULL;
				SET @using_delete_trigger = FALSE;
                
            END IF; 
        END $$
    
CREATE FUNCTION get_pregunta_id(pregunta VARCHAR(255)) RETURNS INT DETERMINISTIC READS SQL DATA
	BEGIN
		DECLARE id_pregunta INT UNSIGNED DEFAULT 0;
        SELECT COALESCE(id, 0) INTO id_pregunta FROM preguntas WHERE cadena = pregunta;
        RETURN id_pregunta;
    END $$
    
CREATE FUNCTION get_respuesta_id(respuesta VARCHAR(255)) RETURNS INT DETERMINISTIC READS SQL DATA
	BEGIN
		DECLARE id_respuesta INT UNSIGNED DEFAULT 0;
        SELECT COALESCE(id, 0) INTO id_respuesta FROM respuestas WHERE cadena = respuesta;
        RETURN id_respuesta;
    END $$
    
CREATE FUNCTION get_pregunta_cadena(id_pregunta INT UNSIGNED) RETURNS VARCHAR(255) DETERMINISTIC READS SQL DATA
	BEGIN
		DECLARE pregunta VARCHAR(255) DEFAULT "Pregunta no existente";
        SELECT COALESCE(cadena, "Pregunta no existente") INTO pregunta FROM preguntas WHERE id = id_pregunta;
        RETURN pregunta;
    END $$
    
CREATE FUNCTION get_respuesta_cadena(id_respuesta INT UNSIGNED) RETURNS VARCHAR(255) DETERMINISTIC READS SQL DATA
	BEGIN
		DECLARE respuesta VARCHAR(255) DEFAULT "Respuesta no existente";
        SELECT COALESCE(respuesta, "Respuesta no existente") INTO respuesta FROM respuestas WHERE id = id_respuesta;
        RETURN respuesta;
    END $$
    
CREATE FUNCTION get_respuesta_from_pregunta(pregunta VARCHAR(255)) RETURNS INT UNSIGNED NOT DETERMINISTIC READS SQL DATA
	BEGIN
		DECLARE pregunta_id INT UNSIGNED DEFAULT 0;
        DECLARE respuesta_id INT UNSIGNED DEFAULT 0;
        DECLARE random_row INT UNSIGNED DEFAULT 0;
        DECLARE i INT UNSIGNED DEFAULT 1;
        DECLARE cur1 CURSOR FOR
					SELECT id FROM respuestas INNER JOIN preguntas_respuestas ON preguntas_respuestas.id_respuesta = respuestas.id
					WHERE preguntas_respuestas.id_pregunta = pregunta_id;
                
		SET pregunta_id = get_pregunta_id(pregunta);
        IF pregunta_id = 0 THEN
			SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'La pregunta no existe';
        ELSE
			SELECT CEIL(RAND() * (SELECT COUNT(*) FROM preguntas_respuestas WHERE id_pregunta = pregunta_id)) INTO random_row;
			OPEN cur1;
				
			read_loop: LOOP
				IF i > random_row THEN
					LEAVE read_loop;
				END IF;
                FETCH cur1 INTO respuesta_id;
				SET i = i+1;
			END LOOP;
            CLOSE cur1;
        END IF;
        
        RETURN respuesta_id;
        
    END $$
    
CREATE PROCEDURE insert_pregunta_respuesta(IN pregunta VARCHAR(255), IN respuesta VARCHAR(255))
	BEGIN
		DECLARE target_pregunta_id INT UNSIGNED;
        DECLARE target_respuesta_id INT UNSIGNED;
        DECLARE CONTINUE HANDLER FOR 1062
			BEGIN
				-- Do nothing
            END;
        SET @using_insert_procedure = TRUE;
        
        SET target_pregunta_id = get_pregunta_id(pregunta);
        SET target_respuesta_id = get_respuesta_id(respuesta);
        
        IF(target_pregunta_id = 0) THEN
			INSERT INTO preguntas (cadena) VALUES (pregunta);
			SET target_pregunta_id = LAST_INSERT_ID();
		END IF;
        
        IF(target_respuesta_id = 0) THEN
			INSERT INTO respuestas (cadena) VALUES (respuesta);
			SET target_respuesta_id = LAST_INSERT_ID();
		END IF;
        
        INSERT INTO preguntas_respuestas (id_pregunta, id_respuesta) VALUES (target_pregunta_id, target_respuesta_id);
        
        SET @using_insert_procedure = FALSE;
    END $$
        
DELIMITER ;

CALL insert_pregunta_respuesta("¿Mercedes me vas a aprobar?", "No te voy a aprobar");
CALL insert_pregunta_respuesta("¿Cómo te llamas?", "Manu");
CALL insert_pregunta_respuesta("¿Cómo te llamas?", "Manuel");
CALL insert_pregunta_respuesta("¿Cómo te llamas?", "Manolo");
CALL insert_pregunta_respuesta("¿Cúal es tu nombre?", "Manu");
CALL insert_pregunta_respuesta("¿Cúal es tu color favorito?", "Azul");

SELECT * FROM preguntas;
SELECT * FROM respuestas;
SELECT * FROM preguntas_respuestas;

/*SET SQL_SAFE_UPDATES=0;*/