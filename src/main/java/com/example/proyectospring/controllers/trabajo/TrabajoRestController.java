package com.example.proyectospring.controllers.trabajo;

import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.expression.EvaluationException;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;

import com.example.proyectospring.entities.trabajador.Trabajador;
import com.example.proyectospring.entities.trabajo.Trabajo;
import com.example.proyectospring.services.trabajo.ITrabajoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.persistence.EntityExistsException;
import jakarta.validation.Valid;


@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping("api/trabajo")
public class TrabajoRestController{
    @Autowired
    private ITrabajoService service;
    @Operation(summary = "Retrieves every job in database.")
    @ApiResponse(responseCode = "200",description = "Succesfully retrieved",content =
                    {@Content(mediaType = "application/json", schema = @Schema(implementation = Trabajo.class))})
    @GetMapping("")
    public List<Trabajo> getAll() {
        return service.getAllTrabajos();
    }
    @Operation(summary = "Retrieves every unasigned job in database.")
    @ApiResponse(responseCode = "200",description = "Succesfully retrieved",content =
                    {@Content(mediaType = "application/json", schema = @Schema(implementation = Trabajo.class))})
    @GetMapping("/not/join")
    public List<Trabajo> getTrabajosNoAsignados() {
        return service.getTrabajosNoAsignados();
    }
    @GetMapping("/not/finished")
    public List<Trabajo> getTrabajosSinFinalizar() {
        return service.getTrabajosSinFinalizar();
    }
    @GetMapping("/finished")
    public List<Trabajo> getTrabajosRealizados() {
        return service.getTrabajosRealizados();
    }
    //TODO Añadir validaciones para fecha y request param por pasar (Comprobar rangos, ver si el id se corresponde en bd)
    @GetMapping("/btwdatesworker")
    public Object getTrabajosFinalizadosFromTrabajadorBtwFechas(@RequestParam String id, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecIni, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecFin){
        Map<String, Object> responseMap=new HashMap<String, Object>();
        try{
        return service.getTrabajosFinalizadosFromTrabajadorBtwFechas(id, fecIni, fecFin);
       }catch(NoSuchElementException e){
        responseMap.put("error", "El id de trabajo no existe en la base de datos");
        responseMap.put("message", e.getMessage());
        return new ResponseEntity<Map<String,Object>>(responseMap,HttpStatus.NOT_FOUND);
       }


    }
    @GetMapping("/prio")
    public List<Trabajo> getTrabajosPrio() {
        return service.getTrabajosPrio();
    }
    @GetMapping("/workerprio")
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
    @GetMapping("/{id}")
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
    @PostMapping("")
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
    @PutMapping("/{id}")
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
    @DeleteMapping("/{id}")
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

    @PatchMapping("/finalizar/{id}")
    @Operation(summary = "Cambia la fecha de finalización del trabajo pasado con el código por parámetro a la fecha actual")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fecha de finalización modificada con éxito"),
            @ApiResponse(responseCode = "404", description = "No existe un trabajo con el código pasado por parámetro"),
            @ApiResponse(responseCode = "400", description = "Error en la formulación de la petición")
    })
    public ResponseEntity<?> finalizarTrabajo(@PathVariable @Parameter(description = "Código del trabajo") String id) {
        try {
            service.finalizarTrabajo(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NotFoundException e) {
            Map<String, Object> responseMap= new HashMap<>();
            responseMap.put("error", "No se ha encontrado un trabajo con el código pasado por parámetro");
            responseMap.put("message", "Not in database");
            return new ResponseEntity<>(responseMap, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            Map<String, Object> responseMap= new HashMap<>();
            responseMap.put("error", "Error al formular la petición");
            responseMap.put("message", "Bad request");
            return new ResponseEntity<>(responseMap, HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/asignar/{id}")
    @Operation(summary = "Asigna el trabajo con el código pasado por parámetro al trabajador con el id pasado por parámetro")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Trabajador asignado con éxito"),
            @ApiResponse(responseCode = "404", description = "No existe un trabajo o trabajador con el código pasado por parámetro"),
            @ApiResponse(responseCode = "400", description = "Error en la formulación de la petición")
    })
    public ResponseEntity<?> asignarTrabajo(@PathVariable @Parameter(description = "Código del trabajo") String id, @RequestParam @Parameter(description = "Id del trabajador") String idT) {
        try {
            service.asignarTrabajo(id, idT);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NotFoundException | DataIntegrityViolationException e) { //Spring tira DataIntegrity cuando no hay trabajador con el id especificado
            Map<String, Object> responseMap= new HashMap<>();
            String palabra = e.getClass() == NotFoundException.class ? "trabajo" : "trabajador";
            responseMap.put("error", "No se ha encontrado un "+ palabra + " con el código pasado por parámetro");
            responseMap.put("message", "Not in database");
            return new ResponseEntity<>(responseMap, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            Map<String, Object> responseMap= new HashMap<>();
            responseMap.put("error", "Error al formular la petición");
            responseMap.put("message", "Bad request");
            return new ResponseEntity<>(responseMap, HttpStatus.BAD_REQUEST);
        }
    }
}