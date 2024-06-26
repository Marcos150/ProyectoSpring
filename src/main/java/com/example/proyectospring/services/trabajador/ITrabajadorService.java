package com.example.proyectospring.services.trabajador;

import com.example.proyectospring.entities.trabajador.Trabajador;
import com.example.proyectospring.entities.trabajo.Trabajo;
import jakarta.persistence.EntityExistsException;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.expression.EvaluationException;

import java.util.List;
import java.util.Map;

public interface ITrabajadorService {
    public List<Trabajador> getAllTrabajadores();
    public Trabajador save(Trabajador trabajador) throws EntityExistsException;
    public Trabajador update(String id, Trabajador trabajador) throws ChangeSetPersister.NotFoundException;
    public Trabajador getTrabajadorById(String id);
    public Map<String,Object> delete(String id) throws ChangeSetPersister.NotFoundException;
    public List<Trabajo> getTrabajosByTrabajador(String id, String password) throws ChangeSetPersister.NotFoundException;
    public List<Trabajo> getTrabajosByTrabajadorFinalizados(String id, String password) throws ChangeSetPersister.NotFoundException;
}
