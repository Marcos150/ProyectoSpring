package com.example.proyectospring.controllers.trabajo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.proyectospring.entities.trabajo.Trabajo;
import com.example.proyectospring.services.trabajo.ITrabajoService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;


@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping("api/trabajo")
public class TrabajoRestController{
    @Autowired
    private ITrabajoService service;
    @GetMapping({"","/"})
    public List<Trabajo> getAll() {
        return service.getAllTrabajos();
    }
    @GetMapping({"/{id}","/{id}/"})
    public ResponseEntity<?> getTrabajoById(@PathVariable String id) {
        Map<String, Object> responseMap=new HashMap<String, Object>();
        try{
            return new ResponseEntity<Trabajo>(service.getTrabajoById(id),HttpStatus.OK);
        }catch(NoSuchElementException e){
            responseMap.put("error", "No se pudo encontrar trabajo con código "+id);
            responseMap.put("message", e.getMessage());
            return new ResponseEntity<Map<String,Object>>(responseMap,HttpStatus.NOT_FOUND);
        }catch(Exception e){
            responseMap.put("error", "Se ha producido un error al formular la petición");
            responseMap.put("message", e.getMessage());
            return new ResponseEntity<Map<String,Object>>(responseMap,HttpStatus.BAD_REQUEST);
        }

    }
    @PostMapping({"","/"})
    public ResponseEntity<?> postTrabajo(@RequestBody(required = false) Trabajo trabajo) {
        Map<String, Object> responseMap=new HashMap<String, Object>();
         try{
            return new ResponseEntity<Trabajo>(service.save(trabajo),HttpStatus.OK);
        }catch(Exception e){
            responseMap.put("error", "Se ha producido un error de inserción");
            responseMap.put("message", e.getMessage());
            return new ResponseEntity<Map<String,Object>>(responseMap,HttpStatus.BAD_REQUEST);
        }

    }
    @PutMapping({"/{id}","/{id}/"})
    public ResponseEntity<?> putTrabajo(@PathVariable String id, @RequestBody(required = false) Trabajo trabajo) {
        Map<String, Object> responseMap=new HashMap<String, Object>();
        if(!id.equals(trabajo.getCodTrabajo())){
            responseMap.put("error", "Las ids respectivas deben ser idénticas");
            responseMap.put("message", "Not equal ids");
            return new ResponseEntity<Map<String,Object>>(responseMap,HttpStatus.BAD_REQUEST);
        }
        try{
            return new ResponseEntity<Trabajo>(service.update(id, trabajo),HttpStatus.OK);
        }catch(NotFoundException e){
            responseMap.put("error", "No se ha encontrado el código del trabajo introducido");
            responseMap.put("message", "Not in database");
            return new ResponseEntity<Map<String,Object>>(responseMap,HttpStatus.NOT_FOUND);
        }catch(Exception e){
            responseMap.put("error", "Se ha producido un error durante la actualización");
            responseMap.put("message", e.getMessage());
            return new ResponseEntity<Map<String,Object>>(responseMap,HttpStatus.BAD_REQUEST);
        }
    }
    @DeleteMapping({"/{id}","/{id}/"})
    public ResponseEntity<?> deleteTrabajo(@PathVariable String id){
        Map<String, Object> responseMap=new HashMap<String, Object>();
        try{
            return new ResponseEntity<Map<String,Object>>(service.delete(id),HttpStatus.OK);
        }catch(NotFoundException e){
            responseMap.put("error", "No se ha encontrado el código del trabajo introducido");
            responseMap.put("message", "Not in database");
            return new ResponseEntity<Map<String,Object>>(responseMap,HttpStatus.NOT_FOUND);
        }catch(Exception e){
            responseMap.put("error", "Se ha producido un error durante el borrado");
            responseMap.put("message", e.getMessage());
            return new ResponseEntity<Map<String,Object>>(responseMap,HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping({"/noJoin","/noJoin/"})
    public List<Trabajo> getTrabajosNoAsignados() {

        return service.getTrabajosNoAsignados();
    }
    
}