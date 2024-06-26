package com.example.proyectospring.services.trabajador.impl;

import com.example.proyectospring.auth.middleware;
import com.example.proyectospring.entities.trabajador.Trabajador;
import com.example.proyectospring.entities.trabajo.Trabajo;
import com.example.proyectospring.repositories.trabajador.TrabajadorRepository;
import com.example.proyectospring.services.trabajador.ITrabajadorService;
import jakarta.persistence.EntityExistsException;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.expression.EvaluationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static java.util.Map.entry;

@Service
public class TrabajadorService implements ITrabajadorService {
    private final TrabajadorRepository repository;

    public TrabajadorService(TrabajadorRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Trabajador> getAllTrabajadores() {
        return (List<Trabajador>) repository.findAll();
    }

    @Override
    public Trabajador save(Trabajador trabajador) throws EntityExistsException {
        if(repository.existsById(trabajador.getIdTrabajador())) throw new EntityExistsException("Already in database, use update endpoint instead");
        return repository.save(trabajador);
    }

    @Override
    public Trabajador update(String id, Trabajador trabajador) throws ChangeSetPersister.NotFoundException {
        if(!repository.existsById(id)) throw new ChangeSetPersister.NotFoundException();
        return repository.save(trabajador);
    }

    @Override
    public Trabajador getTrabajadorById(String id) {
        return repository.findById(id).get();
    }

    @Override
    public Map<String,Object> delete(String id) throws ChangeSetPersister.NotFoundException {
        if(!repository.existsById(id)) throw new ChangeSetPersister.NotFoundException();
        repository.deleteById(id);
        return Map.ofEntries(entry("ok", true));
    }

    @Override
    public List<Trabajo> getTrabajosByTrabajador(String id, String password) throws ChangeSetPersister.NotFoundException {
        if(!repository.existsById(id)) throw new ChangeSetPersister.NotFoundException();
        middleware.authTrabajador(password, repository.findById(id).get());
        return repository.getTrabajosByTrabajador(id, password);
    }

    @Override
    public List<Trabajo> getTrabajosByTrabajadorFinalizados(String id, String password) throws ChangeSetPersister.NotFoundException {
        if(!repository.existsById(id)) throw new ChangeSetPersister.NotFoundException();
        middleware.authTrabajador(password, repository.findById(id).get());
        return repository.getTrabajosByTrabajadorFinalizados(id, password);
    }
}
