/* Populate tabla trabajador */

INSERT INTO trabajador (id_trabajador, dni, nombre, apellidos, especialidad, contrasenya, email) VALUES('1', '12345678A', 'admin', 'admin', 'admin', 'admin', 'admin@example.com');
INSERT INTO trabajador (id_trabajador, dni, nombre, apellidos, especialidad, contrasenya, email) VALUES('2', '87654321Z', 'user1', 'user1Apellido', 'Jardinería', 'password1234', 'user1@example.com');
INSERT INTO trabajador (id_trabajador, dni, nombre, apellidos, especialidad, contrasenya, email) VALUES('3', '10293847E', 'user2', 'user2Apellido', 'Informática', 'password4321', 'user2@example.com');

/* Populate tabla trabajo */

INSERT INTO trabajo (cod_trabajo, categoria, descripcion, fec_ini, fec_fin, tiempo, id_trabajador) VALUES ('1', 'Jardinería', 'Cortar el cesped', '2020-02-02', '2021-02-02', 120, '2');
INSERT INTO trabajo (cod_trabajo, categoria, descripcion, fec_ini, fec_fin, tiempo, id_trabajador) VALUES ('2', 'Informática', 'Arreglar el wifi', '2020-03-03', '2020-03-20', 50, '3');