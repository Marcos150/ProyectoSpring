package com.example.proyectospring.services.trabajador.impl;

import com.example.proyectospring.entities.trabajador.Trabajador;
import com.example.proyectospring.repositories.trabajador.TrabajadorRepository;
import com.example.proyectospring.services.trabajador.ITrabajadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static java.util.Map.entry;

@Service
public class TrabajadorService implements ITrabajadorService {
    @Autowired
    private TrabajadorRepository repository;

    @Override
    public List<Trabajador> getAllTrabajadores() {
        return (List<Trabajador>) repository.findAll();
    }

    @Override
    public Trabajador save(Trabajador trabajador) throws Exception {
        if(repository.existsById(trabajador.getIdTrabajador())) throw new Exception("Already in database, use update endpoint");
        return repository.save(trabajador);
    }

    @Override
    public Trabajador update(String id, Trabajador trabajador) throws Exception {
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
}
