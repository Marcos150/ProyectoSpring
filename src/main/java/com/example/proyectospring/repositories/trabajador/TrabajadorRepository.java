package com.example.proyectospring.repositories.trabajador;

import com.example.proyectospring.entities.trabajador.Trabajador;
import com.example.proyectospring.entities.trabajo.Trabajo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TrabajadorRepository extends CrudRepository<Trabajador, String>
{
    @Query("SELECT t FROM Trabajo t, Trabajador u WHERE t.idTrabajador.idTrabajador = u.idTrabajador AND t.idTrabajador.idTrabajador = :id_trabajador AND u.contrasenya = :password")
    List<Trabajo> getTrabajosByTrabajador(@Param("id_trabajador") String id_trabajador, @Param("password") String password);
    @Query("SELECT t FROM Trabajo t, Trabajador u WHERE t.idTrabajador.idTrabajador = u.idTrabajador AND t.idTrabajador.idTrabajador = :id_trabajador AND u.contrasenya = :password AND t.fecFin IS NOT NULL")
    List<Trabajo> getTrabajosByTrabajadorFinalizados(@Param("id_trabajador") String id_trabajador, @Param("password") String password);
}
