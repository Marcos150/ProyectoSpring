package com.example.proyectospring.services.trabajo.impl;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import static java.util.Map.entry;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

import com.example.proyectospring.auth.middleware;
import com.example.proyectospring.entities.trabajador.Trabajador;
import com.example.proyectospring.entities.trabajo.Trabajo;
import com.example.proyectospring.repositories.trabajador.TrabajadorRepository;
import com.example.proyectospring.repositories.trabajo.TrabajoRepository;
import com.example.proyectospring.services.trabajo.ITrabajoService;

import ch.qos.logback.core.boolex.EvaluationException;
import jakarta.persistence.EntityExistsException;

@Service
public class TrabajoService implements ITrabajoService
{
    @Autowired
    private TrabajoRepository repository;
    @Autowired
    private TrabajadorRepository repository2;

    @Override
    public List<Trabajo> getAllTrabajos() {
        return (List<Trabajo>) repository.findAll();
    }

    @Override
    public Trabajo getTrabajoById(String id) {
        return repository.findById(id).get();
    }

    @Override
    public Trabajo save(Trabajo trabajo) throws EntityExistsException{
        if(repository.existsById(trabajo.getCodTrabajo())) throw new EntityExistsException("Already in database, use update endpoint");
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
    @Override
    public List<Trabajo> getTrabajosNoAsignados(){
        return repository.getTrabajosNoAsignados();
    }

    @Override
    public List<Trabajo> getTrabajosFinalizadosFromTrabajadorBtwFechas(String codTrabajador, Date fecStart, Date fecEnd) {
        return repository.getTrabajosFinalizadosFromTrabajadorBtwFechas(codTrabajador, fecStart, fecEnd);
    }

    @Override
    public List<Trabajo> getTrabajosPrio() {
        return repository.getTrabajosPrio();
    }

    @Override
    public List<Trabajo> getTrabajosRealizados() {
        return repository.getTrabajosRealizados();
    }

    @Override
    public List<Trabajo> getTrabajosSinFinalizar() {
        return repository.getTrabajosSinFinalizar();
    }

    @Override
    public List<Trabajo> getTrabajosTrabajadorByPrio(String codTrabajador, String prio, String pass) throws Exception,EvaluationException,NumberFormatException,NoSuchElementException{
        Trabajador worker=repository2.findById(codTrabajador).get();
        middleware.authTrabajador(pass, worker);
        int prioN=Integer.parseInt(prio);
        return repository.getTrabajosTrabajadorByPrio(codTrabajador,prioN);
    }

    @Override
    public int finalizarTrabajo(String codTrabajador) {
        return repository.finalizarTrabajo(codTrabajador);
    }
}
