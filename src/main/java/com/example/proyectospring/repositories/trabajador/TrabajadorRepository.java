package com.example.proyectospring.repositories.trabajador;

import com.example.proyectospring.entities.trabajador.Trabajador;
import org.springframework.data.repository.CrudRepository;

public interface TrabajadorRepository extends CrudRepository<Trabajador, String>
{
}
