-- Drop tables if they exist
DROP TABLE IF EXISTS persona;
DROP TABLE IF EXISTS citas;
DROP TABLE IF EXISTS mascotas;
DROP TABLE IF EXISTS horarios;
DROP TABLE IF EXISTS clientes;
DROP TABLE IF EXISTS administradores;

-- Create persona table
CREATE TABLE personas (
  cedula VARCHAR(10) PRIMARY KEY,
  nombres TEXT NOT NULL,
  apellidos TEXT NOT NULL
);

-- Create clientes table
CREATE TABLE clientes (
  cedula VARCHAR(10) PRIMARY KEY,
  direccion TEXT,
  telefono VARCHAR(10),
  email TEXT,
  FOREIGN KEY (cedula) REFERENCES personas(cedula)
);

-- Create mascotas table
CREATE TABLE mascotas (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  cliente_cedula VARCHAR(10),
  nombre TEXT NOT NULL,
  tipo_animal VARCHAR(50),
  fecha_nacimiento DATE,
  peso DECIMAL(10, 2),
  raza TEXT,
  vacunas BOOLEAN,
  observaciones TEXT,
  FOREIGN KEY (cliente_cedula) REFERENCES clientes(cedula)
);

-- Create horarios table
CREATE TABLE horarios (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  hora TIME NOT NULL,
  disponibilidad BOOLEAN NOT NULL
);

-- Create citas table
CREATE TABLE citas (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  mascota_id BIGINT,
  horario_id BIGINT,
  motivo_consulta TEXT,
  FOREIGN KEY (mascota_id) REFERENCES mascotas(id),
  FOREIGN KEY (horario_id) REFERENCES horarios(id)
);

-- Create administradores table
CREATE TABLE administradores (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  usuario VARCHAR(255) UNIQUE NOT NULL,
  contrasena VARCHAR(255) NOT NULL
);

-- Drop the auditoria table if it exists
DROP TABLE IF EXISTS aud_citas;

-- Create the auditoria table
CREATE TABLE aud_citas (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  nombre_mascota TEXT,
  hora_horario TIME,
  motivo_consulta TEXT,
  accion TEXT NOT NULL,
  fecha_accion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

DELIMITER //
CREATE PROCEDURE `Sp_crear_citas`(
	out p_citas_id bigint,
	inout p_mascota_id bigint,
    inout p_horario_id BIGINT,
    inout p_motivo_consulta TEXT
)
BEGIN
	insert into citas(mascota_id, horario_id, motivo_consulta) values (p_mascota_id, p_horario_id, p_motivo_consulta) ;
    SET p_citas_id = LAST_INSERT_ID();
    
    update horarios set disponibilidad = false where id = p_horario_id;
END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE `Sp_obtener_citas`(
	inout p_citas_id bigint,
	out p_mascota_id bigint,
    out p_horario_id BIGINT,
    out p_motivo_consulta TEXT
)
BEGIN
	select id, mascota_id, horario_id, motivo_consulta
    into p_citas_id, p_mascota_id, p_horario_id, p_motivo_consulta
    from citas where id = p_citas_id;
END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE `Sp_modificar_citas`(
	inout p_citas_id bigint,
	out p_mascota_id bigint,
    inout p_horario_id BIGINT,
    inout p_motivo_consulta TEXT
)
BEGIN
	declare v_horario_ant_id int;
	select horario_id into v_horario_ant_id from citas where id = p_citas_id;
	update citas set horario_id = p_horario_id, motivo_consulta = p_motivo_consulta
    where id = p_citas_id;
    update horarios set disponibilidad = false where id = p_horario_id;
    update horarios set disponibilidad = true where id = v_horario_ant_id;
END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE `Sp_eliminar_citas`(
	inout p_citas_id bigint,
	out p_mascota_id bigint,
    out p_horario_id BIGINT
)
BEGIN
	delete from citas where id = p_citas_id;
    update horarios set disponibilidad = true where id = p_horario_id;
END //
DELIMITER ;

DELIMITER //
CREATE TRIGGER after_crear_citas
AFTER INSERT ON citas
FOR EACH ROW
BEGIN
    INSERT INTO aud_citas (nombre_mascota, hora_horario, motivo_consulta, accion, fecha_accion)
    VALUES (
        (SELECT nombre FROM mascotas WHERE id = NEW.mascota_id),
        (SELECT hora FROM horarios WHERE id = NEW.horario_id),
        NEW.motivo_consulta,
        'INSERT',
        CURRENT_TIMESTAMP
    );
END //
DELIMITER ;

DELIMITER //
CREATE TRIGGER after_modificar_citas
AFTER UPDATE ON citas
FOR EACH ROW
BEGIN
    INSERT INTO aud_citas (nombre_mascota, hora_horario, motivo_consulta, accion, fecha_accion)
    VALUES (
        (SELECT nombre FROM mascotas WHERE id = NEW.mascota_id),
        (SELECT hora FROM horarios WHERE id = NEW.horario_id),
        NEW.motivo_consulta,
        'UPDATE',
        CURRENT_TIMESTAMP
    );
END //
DELIMITER ;

DELIMITER //
CREATE TRIGGER after_eliminar_citas
AFTER DELETE ON citas
FOR EACH ROW
BEGIN
    INSERT INTO aud_citas (nombre_mascota, hora_horario, motivo_consulta, accion, fecha_accion)
    VALUES (
        (SELECT nombre FROM mascotas WHERE id = OLD.mascota_id),
        (SELECT hora FROM horarios WHERE id = OLD.horario_id),
        OLD.motivo_consulta,
        'DELETE',
        CURRENT_TIMESTAMP
    );
END //
DELIMITER ;

INSERT INTO personas (cedula, nombres, apellidos) VALUES
('1234567890', 'Juan', 'Pérez'),
('0987654321', 'María', 'Gómez'),
('1122334455', 'Carlos', 'López');

INSERT INTO clientes (cedula, direccion, telefono, email) VALUES
('1234567890', 'Calle Falsa 123', '5551234567', 'juan.perez@example.com'),
('0987654321', 'Avenida Siempre Viva 742', '5559876543', 'maria.gomez@example.com'),
('1122334455', 'Boulevard de los Sueños 456', '5556543210', 'carlos.lopez@example.com');

INSERT INTO mascotas (cliente_cedula, nombre, tipo_animal, fecha_nacimiento, peso, raza, vacunas, observaciones) VALUES 
('1234567890', 'Firulais', 'perro', '2018-05-20', 10.5, 'Labrador', TRUE, 'Alergia a los ácaros'),
('0987654321', 'Miau', 'gato', '2020-08-15', 3.2, 'Siames', TRUE, ''),
('1122334455', 'Rex', 'perro', '2019-11-30', 25.0, 'Pastor Alemán', FALSE, 'Problemas de cadera');

INSERT INTO horarios (hora, disponibilidad) VALUES 
('09:00:00', FALSE),
('14:00:00', FALSE),
('16:00:00', FALSE);
