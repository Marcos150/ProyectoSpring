package com.example.proyectospring.services.trabajador;

import com.example.proyectospring.entities.trabajador.Trabajador;
import org.springframework.data.crossstore.ChangeSetPersister;

import java.util.List;
import java.util.Map;

public interface ITrabajadorService {
    public List<Trabajador> getAllTrabajadores();
    public Trabajador save(Trabajador trabajador) throws Exception;
    public Trabajador update(String id, Trabajador trabajador) throws Exception;
    public Trabajador getTrabajadorById(String id);
    public Map<String,Object> delete(String id) throws ChangeSetPersister.NotFoundException;
}
