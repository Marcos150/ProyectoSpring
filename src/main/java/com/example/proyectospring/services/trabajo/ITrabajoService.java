package com.example.proyectospring.services.trabajo;

import java.util.List;
import java.util.Map;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;

import com.example.proyectospring.entities.trabajo.Trabajo;

public interface ITrabajoService
{
    public List<Trabajo> getAllTrabajos();
    public Trabajo getTrabajoById(String id);
    public Trabajo save(Trabajo trabajo) throws Exception;
    public Trabajo update(String code,Trabajo trabajo) throws NotFoundException;
    public Map<String,Object> delete(String code) throws NotFoundException;
}
