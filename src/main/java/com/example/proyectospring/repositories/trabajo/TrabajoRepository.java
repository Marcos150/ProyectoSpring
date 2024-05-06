package com.example.proyectospring.repositories.trabajo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.example.proyectospring.entities.trabajo.Trabajo;

public interface TrabajoRepository extends CrudRepository<Trabajo,String>
{
    @Query("SELECT t FROM Trabajo t WHERE t.idTrabajador is NULL")
    public List<Trabajo> getTrabajosNoAsignados();
}
