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
        
CREATE TRIGGER after_delete_pregunta BEFORE DELETE ON preguntas
	FOR EACH ROW
		BEGIN
			SET @using_delete_trigger = TRUE;
            SET @count = 0;
            SELECT COUNT(*) FROM preguntas_respuestas WHERE id_pregunta = OLD.id INTO count;
            IF @count = 1
				DELETE respuestas FROM respuestas WHERE 
            END IF;
			DELETE respuestas FROM respuestas
				LEFT JOIN preguntas_respuestas ON preguntas_respuestas.id_respuesta = respuestas.id
					WHERE preguntas_respuestas.id_pregunta IS NULL;
			SET @using_delete_trigger = FALSE;
        END $$
        
CREATE TRIGGER block_respuestas_delete BEFORE DELETE ON respuestas
	FOR EACH ROW
		BEGIN
			IF NOT @using_delete_trigger THEN
				SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'No se puede borrar una respuesta directamente';
			END IF;
        END $$
    
CREATE FUNCTION get_pregunta_id(pregunta VARCHAR(255)) RETURNS INT DETERMINISTIC
	BEGIN
		DECLARE id_pregunta INT DEFAULT 0;
        SELECT COALESCE(id, 0) INTO id_pregunta FROM preguntas WHERE cadena = pregunta;
        RETURN id_pregunta;
    END $$
    
CREATE FUNCTION get_respuesta_id(respuesta VARCHAR(255)) RETURNS INT DETERMINISTIC
	BEGIN
		DECLARE id_respuesta INT DEFAULT 0;
        SELECT COALESCE(id, 0) INTO id_respuesta FROM respuestas WHERE cadena = respuesta;
        RETURN id_respuesta;
    END $$
    
CREATE PROCEDURE insert_pregunta_respuesta(IN pregunta VARCHAR(255), IN respuesta VARCHAR(255))
	BEGIN
		DECLARE last_pregunta_id INT;
        DECLARE last_respuesta_id INT;
        SET @using_insert_procedure = TRUE;
        
        IF(get_pregunta_id(pregunta) = 0) THEN
			INSERT INTO preguntas (cadena) VALUES (pregunta);
			SET last_pregunta_id = LAST_INSERT_ID();
		ELSE
			SET last_pregunta_id = get_pregunta_id(pregunta);
		END IF;
        
        IF(get_respuesta_id(respuesta) = 0) THEN
			INSERT INTO respuestas (cadena) VALUES (respuesta);
			SET last_respuesta_id = LAST_INSERT_ID();
		ELSE
			SET last_respuesta_id = get_respuesta_id(respuesta);
		END IF;
        
        INSERT INTO preguntas_respuestas (id_pregunta, id_respuesta) VALUES (last_pregunta_id, last_respuesta_id);
        #Capturar excepción
        SET @using_insert_procedure = FALSE;
    END $$
        
DELIMITER ;

CALL insert_pregunta_respuesta("¿Mercedes me vas a aprobar?", "No te voy a aprobar");
CALL insert_pregunta_respuesta("¿Cómo te llamas?", "Manu");
CALL insert_pregunta_respuesta("¿Cúal es tu nombre?", "Manu");
CALL insert_pregunta_respuesta("¿Cúal es tu color favorito?", "Azul");

SELECT * FROM preguntas;
SELECT * FROM respuestas;
SELECT * FROM preguntas_respuestas;

SET SQL_SAFE_UPDATES=0;
DELETE FROM preguntas WHERE cadena = "¿Cómo te llamas?";