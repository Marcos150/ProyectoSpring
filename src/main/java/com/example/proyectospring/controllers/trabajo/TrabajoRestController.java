package com.example.proyectospring.controllers.trabajo;

import java.sql.Date;
import java.util.*;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.expression.EvaluationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.proyectospring.entities.trabajo.Trabajo;
import com.example.proyectospring.services.trabajo.ITrabajoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.persistence.EntityExistsException;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;


@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping("api/trabajo")
public class TrabajoRestController{
    @Autowired
    private ITrabajoService service;
    @Operation(summary = "Retrieves every job in database.",description = "")
    @ApiResponse(responseCode = "200",description = "Succesfully retrieved")
    @GetMapping({"","/"})
    public List<Trabajo> getAll() {
        return service.getAllTrabajos();
    }
    @GetMapping({"/not/join","/not/join/"})
    public List<Trabajo> getTrabajosNoAsignados() {
        return service.getTrabajosNoAsignados();
    }
    @GetMapping({"/not/finished","/not/finished/"})
    public List<Trabajo> getTrabajosSinFinalizar() {
        return service.getTrabajosSinFinalizar();
    }
    @GetMapping({"/finished","/finished/"})
    public List<Trabajo> getTrabajosRealizados() {
        return service.getTrabajosRealizados();
    }
    //TODO Añadir validaciones para fecha y request param por pasar (Comprobar rangos, ver si el id se corresponde en bd)
    @GetMapping({"/btwdatesworker","/btwdatesworker/"})
    public List<Trabajo> getTrabajosFinalizadosFromTrabajadorBtwFechas(@RequestParam String id, @RequestParam Date fecIni, @RequestParam Date fecFin) {
        return service.getTrabajosFinalizadosFromTrabajadorBtwFechas(null, null, null);
    }
    @GetMapping({"/prio","/prio/"})
    public List<Trabajo> getTrabajosPrio() {
        return service.getTrabajosPrio();
    }
    @GetMapping({"/workerprio","/workerprio/"})
    public ResponseEntity<?> getTrabajosTrabajadorPrio(@RequestParam String id, @RequestParam String prio, @RequestHeader("password")String pass) {
        Map<String, Object> responseMap=new HashMap<String, Object>();
        try{
            return new ResponseEntity<List<Trabajo>>(service.getTrabajosTrabajadorByPrio(id, prio,pass),HttpStatus.OK);
        }catch(NumberFormatException e){
            responseMap.put("error", "No se pasó un número adecuado en la prioridad");
            responseMap.put("message", e.getMessage());
        }
        catch(NoSuchElementException e){
            responseMap.put("error", "No se encontró el trabajador con id: "+id);
            responseMap.put("message", e.getMessage());
            return new ResponseEntity<Map<String,Object>>(responseMap,HttpStatus.NOT_FOUND);
        }
        catch(EvaluationException e){
            responseMap.put("error", "Hubo un problema de autorización");
            responseMap.put("message", e.getMessage());
            return new ResponseEntity<Map<String,Object>>(responseMap,HttpStatus.UNAUTHORIZED);
        }
        catch(Exception e){
            responseMap.put("error", "No pudo completar la operación");
            responseMap.put("message", e.getMessage());
        }
        return new ResponseEntity<Map<String,Object>>(responseMap,HttpStatus.BAD_REQUEST);

    }
    @GetMapping({"/{id}","/{id}/"})
    public ResponseEntity<?> getTrabajoById(@PathVariable @Parameter(name = "id",description = "lord",example = "1") String id) {
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
    public ResponseEntity<?> postTrabajo(@RequestBody(required = false) @Valid() Trabajo trabajo){

        Map<String, Object> responseMap=new HashMap<String, Object>();
        try{
            Trabajo job=service.save(trabajo);
            return new ResponseEntity<Trabajo>(job,HttpStatus.OK);
        }catch (EntityExistsException e){
            responseMap.put("error", e.getMessage());
            return new ResponseEntity<Map<String,Object>>(responseMap,HttpStatus.BAD_REQUEST);
        }


    }
    @PutMapping({"/{id}","/{id}/"})
    public ResponseEntity<?> putTrabajo(@PathVariable String id, @RequestBody(required = false) @Valid() Trabajo trabajo) {
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
    @GetMapping({"/"})
    public String getMethodName(@RequestParam String param) {
        return new String();
    }

    @PatchMapping("/finalizar/{id}")
    @Operation(summary = "Cambia la fecha de finalización del trabajo pasado con el código por parámetro a la fecha actual")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fecha de finalización modificada con éxito"),
            @ApiResponse(responseCode = "404", description = "No existe un trabajo con el código pasado por parámetro", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Object.class)) }),
            @ApiResponse(responseCode = "400", description = "Error en la formulación de la petición", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Object.class)) })
    })
    public ResponseEntity<?> finalizarTrabajo(@PathVariable @Parameter(description = "Código del trabajo") String id) {
        if (service.finalizarTrabajo(id) <= 0) {
            Map<String, Object> responseMap= new HashMap<>();
            responseMap.put("error", "No se ha encontrado un trabajo con el código pasado por parámetro");
            responseMap.put("message", "Not in database");
            return new ResponseEntity<>(responseMap, HttpStatus.NOT_FOUND);
        }
        else
            return new ResponseEntity<>(HttpStatus.OK);
    }
}