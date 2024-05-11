package com.example.proyectospring.services.trabajador;

import com.example.proyectospring.entities.trabajador.Trabajador;
import com.example.proyectospring.entities.trabajo.Trabajo;
import org.springframework.data.crossstore.ChangeSetPersister;

import java.util.List;
import java.util.Map;

public interface ITrabajadorService {
    public List<Trabajador> getAllTrabajadores();
    public Trabajador save(Trabajador trabajador) throws Exception;
    public Trabajador update(String id, Trabajador trabajador) throws Exception;
    public Trabajador getTrabajadorById(String id);
    public Map<String,Object> delete(String id) throws ChangeSetPersister.NotFoundException;
    public List<Trabajo> getTrabajosByTrabajador(String id, String password);
    public List<Trabajo> getTrabajosByTrabajadorFinalizados(String id, String password);
}
