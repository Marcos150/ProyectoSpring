package com.example.proyectospring.entities.trabajador;

import com.example.proyectospring.entities.trabajo.Trabajo;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "trabajador")
public class Trabajador implements java.io.Serializable
{
    @Id
    @Column(name = "id_trabajador", nullable = false, length = 5)
    private String idTrabajador;

    @Column(name = "dni", nullable = false, length = 9)
    private String dni;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "apellidos", nullable = false, length = 100)
    private String apellidos;

    @Column(name = "especialidad", nullable = false, length = 50)
    private String especialidad;

    @Column(name = "contrasenya", nullable = false, length = 50)
    private String contrasenya;

    @Column(name = "email", nullable = false, length = 150)
    private String email;
    @OneToMany(mappedBy = "idTrabajador",fetch = FetchType.LAZY)
    @JsonManagedReference
    private Set<Trabajo> trabajos = new LinkedHashSet<>();

    public String getIdTrabajador()
    {
        return idTrabajador;
    }

    public void setIdTrabajador(String idTrabajador)
    {
        this.idTrabajador = idTrabajador;
    }

    public String getDni()
    {
        return dni;
    }

    public void setDni(String dni)
    {
        this.dni = dni;
    }

    public String getNombre()
    {
        return nombre;
    }

    public void setNombre(String nombre)
    {
        this.nombre = nombre;
    }

    public String getApellidos()
    {
        return apellidos;
    }

    public void setApellidos(String apellidos)
    {
        this.apellidos = apellidos;
    }

    public String getEspecialidad()
    {
        return especialidad;
    }

    public void setEspecialidad(String especialidad)
    {
        this.especialidad = especialidad;
    }

    public String getContrasenya()
    {
        return contrasenya;
    }

    public void setContrasenya(String contrasenya)
    {
        this.contrasenya = contrasenya;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public Set<Trabajo> getTrabajos()
    {
        return trabajos;
    }

    public void setTrabajos(Set<Trabajo> trabajos)
    {
        this.trabajos = trabajos;
    }

}