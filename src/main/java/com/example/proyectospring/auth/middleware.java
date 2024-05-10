package com.example.proyectospring.auth;

import org.springframework.expression.EvaluationException;

import com.example.proyectospring.entities.trabajador.Trabajador;

public class middleware {
    public static void authTrabajador(String password, Trabajador worker) throws EvaluationException{
        if(!password.equals(worker.getContrasenya()))throw new EvaluationException("La contraseña introducida no es válida");
    }
}
