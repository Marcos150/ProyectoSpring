package com.example.proyectospring.entities.trabajador;

import com.example.proyectospring.entities.trabajo.Trabajo;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "trabajador")
public class Trabajador implements java.io.Serializable
{
    @Id
    @Column(name = "id_trabajador", nullable = false, length = 5)
    @Size(max = 5, message = "La longitud máxima del id del trabajador es de 5 caracteres")
    @NotNull(message = "No puede ser nulo")
    @NotEmpty(message = "El campo id trabajador no puede estar vacío")
    private String idTrabajador;

    @Column(name = "dni", nullable = false, length = 9)
    @Size(max = 9, min = 9, message = "La longitud del dni es de 9 caracteres")
    @NotNull(message = "No puede ser nulo")
    @NotEmpty(message = "El campo dni no puede estar vacío")
    private String dni;

    @Column(name = "nombre", nullable = false, length = 100)
    @Size(max = 100, message = "La longitud máxima del nombre es de 100 caracteres")
    @NotNull(message = "No puede ser nulo")
    @NotEmpty(message = "El campo nombre no puede estar vacío")
    private String nombre;

    @Column(name = "apellidos", nullable = false, length = 100)
    @Size(max = 100, message = "La longitud máxima de los apellidos es de 100 caracteres")
    @NotNull(message = "No puede ser nulo")
    @NotEmpty(message = "El campo apellidos no puede estar vacío")
    private String apellidos;

    @Column(name = "especialidad", nullable = false, length = 50)
    @Size(max = 100, message = "La longitud máxima de la especialidad es de 100 caracteres")
    @NotNull(message = "No puede ser nulo")
    @NotEmpty(message = "El campo especialidad no puede estar vacío")
    private String especialidad;

    @Column(name = "contrasenya", nullable = false, length = 50)
    @Size(max = 50, message = "La longitud máxima de la contraseña es de 50 caracteres")
    @NotNull(message = "No puede ser nulo")
    @NotEmpty(message = "El campo contraseña no puede estar vacío")
    private String contrasenya;

    @Column(name = "email", nullable = false, length = 150)
    @Size(max = 100, message = "La longitud máxima del email es de 100 caracteres") //TODO: Realmente la longitud maxima de un correo es de 255 chars
    @NotNull(message = "No puede ser nulo")
    @NotEmpty(message = "El campo email no puede estar vacío")
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