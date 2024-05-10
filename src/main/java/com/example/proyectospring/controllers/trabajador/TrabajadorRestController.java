package com.example.proyectospring.controllers.trabajador;

import com.example.proyectospring.entities.trabajador.Trabajador;
import com.example.proyectospring.services.trabajador.ITrabajadorService;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping("/api/trabajadores")
public class TrabajadorRestController {
    private final ITrabajadorService service;

    public TrabajadorRestController(ITrabajadorService service) {
        this.service = service;
    }

    @GetMapping({"", "/"})
    public List<Trabajador> getAll() {
        return service.getAllTrabajadores();
    }

    @GetMapping( {"/{id}"})
    public ResponseEntity<?> getTrabajadorById(@PathVariable String id) {
        Map<String, Object> responseMap = new HashMap<>();
        try {
            return new ResponseEntity<>(service.getTrabajadorById(id), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            responseMap.put("error", "No se pudo encontrar un trabajador con el id " + id);
            responseMap.put("message", e.getMessage());
            return new ResponseEntity<>(responseMap, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            responseMap.put("error", "Se ha producido un error al formular la petición");
            responseMap.put("message", e.getMessage());
            return new ResponseEntity<>(responseMap, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping({"", "/"})
    public ResponseEntity<?> postTrabajo(@RequestBody(required = false) Trabajador trabajador) {
        Map<String, Object> responseMap = new HashMap<>();
        try {
            return new ResponseEntity<>(service.save(trabajador), HttpStatus.OK);
        } catch (Exception e) {
            responseMap.put("error", "Se ha producido un error de inserción");
            responseMap.put("message", e.getMessage());
            return new ResponseEntity<>(responseMap, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping({"/{id}"})
    public ResponseEntity<?> putTrabajador(@PathVariable String id, @RequestBody(required = false) Trabajador trabajador) {
        Map<String, Object> responseMap = new HashMap<>();
        if (!id.equals(trabajador.getIdTrabajador())) trabajador.setIdTrabajador(id);
        try {
            return new ResponseEntity<>(service.update(id, trabajador), HttpStatus.OK);
        } catch (ChangeSetPersister.NotFoundException e) {
            responseMap.put("error", "No se ha encontrado el código del trabajo introducido");
            responseMap.put("message", "Not in database");
            return new ResponseEntity<>(responseMap, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            responseMap.put("error", "Se ha producido un error durante la actualización");
            responseMap.put("message", e.getMessage());
            return new ResponseEntity<>(responseMap, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping({"/{id}"})
    public ResponseEntity<?> deleteTrabajo(@PathVariable String id) {
        Map<String, Object> responseMap = new HashMap<>();
        try {
            return new ResponseEntity<>(service.delete(id), HttpStatus.OK);
        } catch (ChangeSetPersister.NotFoundException e) {
            responseMap.put("error", "No se ha encontrado el id de trabajador introducido");
            responseMap.put("message", "Not in database");
            return new ResponseEntity<>(responseMap, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            responseMap.put("error", "Se ha producido un error durante el borrado");
            responseMap.put("message", e.getMessage());
            return new ResponseEntity<>(responseMap, HttpStatus.BAD_REQUEST);
        }
    }
}