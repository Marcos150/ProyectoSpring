package com.example.proyectospring.services.trabajo.impl;

import java.util.List;
import java.util.Map;
import static java.util.Map.entry;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import com.example.proyectospring.entities.trabajo.Trabajo;
import com.example.proyectospring.repositories.trabajo.TrabajoRepository;
import com.example.proyectospring.services.trabajo.ITrabajoService;

@Service
public class TrabajoService implements ITrabajoService
{
    @Autowired
    private TrabajoRepository repository;

    @Override
    public List<Trabajo> getAllTrabajos() {
        return (List<Trabajo>) repository.findAll();
    }

    @Override
    public Trabajo getTrabajoById(String id) {
        return repository.findById(id).get();
    }

    @Override
    public Trabajo save(Trabajo trabajo) throws Exception {
        if(repository.existsById(trabajo.getCodTrabajo())) throw new Exception("Already in database, use update endpoint");
        return repository.save(trabajo);
    }
    @Override
    public Trabajo update(String code,Trabajo trabajo) throws NotFoundException{
        if(!repository.existsById(code)) throw new NotFoundException();
        return repository.save(trabajo);
    }

    @Override
    public Map<String,Object> delete(String code) throws NotFoundException {
        if(!repository.existsById(code)) throw new NotFoundException();
        repository.deleteById(code);
        return Map.ofEntries(entry("ok", true));
    }
    public List<Trabajo> getTrabajosNoAsignados(){
        return repository.getTrabajosNoAsignados();
    }
    
}
