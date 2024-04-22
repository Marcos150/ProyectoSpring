CREATE DATABASE proyecto_spring;

DROP TABLE IF EXISTS trabajo;
DROP TABLE IF EXISTS trabajador;

CREATE TABLE IF NOT EXISTS trabajador
(
    id_trabajador VARCHAR(5) PRIMARY KEY,
    dni           VARCHAR(9) UNIQUE   NOT NULL,
    nombre        VARCHAR(100)        NOT NULL,
    apellidos     VARCHAR(100)        NOT NULL,
    especialidad  VARCHAR(50)         NOT NULL,
    contrasenya    VARCHAR(50)         NOT NULL,
    email         VARCHAR(150) UNIQUE NOT NULL
    );

CREATE TABLE IF NOT EXISTS trabajo
(
    cod_trabajo   VARCHAR(5) PRIMARY KEY,
    categoria     VARCHAR(50)  NOT NULL,
    descripcion   VARCHAR(500) NOT NULL,
    fec_ini       DATE         NOT NULL,
    fec_fin       DATE,
    tiempo        NUMERIC(4, 1),
    id_trabajador VARCHAR(5),
    CONSTRAINT fk_trabajador FOREIGN KEY (id_trabajador) REFERENCES trabajador (id_trabajador)
    );