package com.example.proyectospring.services.trabajo;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;

import com.example.proyectospring.entities.trabajo.Trabajo;

import ch.qos.logback.core.boolex.EvaluationException;
import jakarta.persistence.EntityExistsException;


public interface ITrabajoService
{
    public List<Trabajo> getAllTrabajos();
    public Trabajo getTrabajoById(String id);
    public Trabajo save(Trabajo trabajo) throws EntityExistsException;
    public Trabajo update(String code,Trabajo trabajo) throws NotFoundException;
    public Map<String,Object> delete(String code) throws NotFoundException;
    public List<Trabajo> getTrabajosNoAsignados();
    public List<Trabajo> getTrabajosSinFinalizar();
    public List<Trabajo> getTrabajosRealizados();
    public List<Trabajo> getTrabajosFinalizadosFromTrabajadorBtwFechas(String codTrabajador,java.util.Date fecStart,java.util.Date fecEnd);
    public List<Trabajo> getTrabajosPrio();
    public List<Trabajo> getTrabajosTrabajadorByPrio(String codTrabajador, String prio, String pass) throws EvaluationException,NoSuchElementException,Exception,NumberFormatException;
}
