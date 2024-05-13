package com.example.proyectospring.entities.trabajo;

import com.example.proyectospring.entities.trabajador.Trabajador;
import com.example.proyectospring.validators.api.DateRange;
import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "trabajo")
@DateRange(before = "fecIni",after="fecFin",message = "La fecha inicial no debe superar a la fecha de fin")
public class Trabajo implements java.io.Serializable
{
    @Id
    @Column(name = "cod_trabajo", nullable = false, length = 5)
    @Size(max = 5, message = "La longitud máxima del código de trabajo es de 5 caracteres")
    @NotNull(message = "No puede ser nulo")
    @NotEmpty(message = "El campo código trabajo no puede estar vacío")
    private String codTrabajo;

    @Column(name = "categoria", nullable = false, length = 50)
    @Size(max = 50, message = "La longitud máxima de la categoría máxima es de 50 caracteres")
    @NotNull(message = "No puede ser nulo")
    @NotEmpty(message = "El campo categoría no puede estar vacío")
    private String categoria;

    @Column(name = "descripcion", nullable = false, length = 500)
    @Size(max = 500, message = "La longitud máxima de la descripción es de 500 caracteres")
    @NotNull(message = "No puede ser nulo")
    private String descripcion;

    @Column(name = "fec_ini", nullable = false)
    @NotNull(message = "No puede ser nulo")
    private LocalDate fecIni;

    @Column(name = "fec_fin")
    private LocalDate fecFin;

    @Column(name = "tiempo", precision = 4, scale = 1)
    @Min(value = 0, message = "La longitud mínima es de 0")
    private BigDecimal tiempo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_trabajador")
    @JsonBackReference
    private Trabajador idTrabajador;

    @Column(name = "prioridad", precision = 1, nullable = false)
    @Min(value = 1, message = "Rango admitido en prioridad: 1-4")
    @Max(value = 4, message = "Rango admitido en prioridad: 1-4")
    @NotNull(message = "No puede ser nulo")
    private BigDecimal prioridad;

    public Trabajo(){}

    public Trabajo(String codTrabajo, @NotBlank(message = "Campo categoría") String categoria, String descripcion,
            LocalDate fecIni, LocalDate fecFin,
            @Min(0) @NotNull(message = "El tiempo debe indicarse") BigDecimal tiempo, Trabajador idTrabajador,
            @Min(value = 1, message = "Rango admitido en prioridad: 1-4") @Max(value = 4, message = "Rango admitido en prioridad: 1-4") BigDecimal prioridad) {
        this.codTrabajo = codTrabajo;
        this.categoria = categoria;
        this.descripcion = descripcion;
        this.fecIni = fecIni;
        this.fecFin = fecFin;
        this.tiempo = tiempo;
        this.idTrabajador = idTrabajador;
        this.prioridad = prioridad;
    }

    public String getCodTrabajo()
    {
        return codTrabajo;
    }

    public void setCodTrabajo(String codTrabajo)
    {
        this.codTrabajo = codTrabajo;
    }

    public String getCategoria()
    {
        return categoria;
    }

    public void setCategoria(String categoria)
    {
        this.categoria = categoria;
    }

    public String getDescripcion()
    {
        return descripcion;
    }

    public void setDescripcion(String descripcion)
    {
        this.descripcion = descripcion;
    }

    public LocalDate getFecIni()
    {
        return fecIni;
    }

    public void setFecIni(LocalDate fecIni)
    {
        this.fecIni = fecIni;
    }

    public LocalDate getFecFin()
    {
        return fecFin;
    }

    public void setFecFin(LocalDate fecFin)
    {
        this.fecFin = fecFin;
    }

    public BigDecimal getTiempo()
    {
        return tiempo;
    }

    public void setTiempo(BigDecimal tiempo)
    {
        this.tiempo = tiempo;
    }

    public Trabajador getIdTrabajador()
    {
        return idTrabajador;
    }

    public void setIdTrabajador(Trabajador idTrabajador)
    {
        this.idTrabajador = idTrabajador;
    }

    public BigDecimal getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(BigDecimal prioridad) {
        this.prioridad = prioridad;
    }
}
