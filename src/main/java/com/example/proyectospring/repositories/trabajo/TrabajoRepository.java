package com.example.proyectospring.repositories.trabajo;

import org.springframework.data.repository.CrudRepository;

import com.example.proyectospring.entities.trabajo.Trabajo;

public interface TrabajoRepository extends CrudRepository<Trabajo,String>
{
}
