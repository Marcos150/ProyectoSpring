package com.example.proyectospring.repositories.trabajo;

import org.springframework.data.jpa.repository.Query;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.example.proyectospring.entities.trabajo.Trabajo;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TrabajoRepository extends CrudRepository<Trabajo,String>
{
    @Query("SELECT t FROM Trabajo t WHERE t.idTrabajador is NULL")
    public List<Trabajo> getTrabajosNoAsignados();
    @Query("SELECT t FROM Trabajo t WHERE t.idTrabajador is not NULL and t.fecFin is NULL")
    public List<Trabajo> getTrabajosSinFinalizar();
    @Query("SELECT t FROM Trabajo t WHERE t.idTrabajador is not NULL and t.fecFin is not NULL")
    public List<Trabajo> getTrabajosRealizados();
    @Query("SELECT t FROM Trabajo t WHERE t.idTrabajador=:codT and t.fecFin between :fecStart AND :fecEnd")
    public List<Trabajo> getTrabajosFinalizadosFromTrabajadorBtwFechas(@Param("codT")String codTrabajador,@Param("fecStart")Date fecStart,@Param("fecEnd")Date fecEnd);
    @Query("SELECT t FROM Trabajo t ORDER BY prioridad")
    public List<Trabajo> getTrabajosPrio();
    @Query("SELECT t FROM Trabajo t WHERE t.idTrabajador.idTrabajador=:codT AND t.prioridad=:prio")
    public List<Trabajo> getTrabajosTrabajadorByPrio(@Param("codT")String codTrabajador,@Param("prio") int prio);
}
