package com.example.proyectospring.controllers.trabajador;

import com.example.proyectospring.entities.trabajador.Trabajador;
import com.example.proyectospring.services.trabajador.ITrabajadorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.expression.EvaluationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping("/api/trabajador")
public class TrabajadorRestController {
    private final ITrabajadorService service;

    public TrabajadorRestController(ITrabajadorService service) {
        this.service = service;
    }

    @Operation(summary = "Retrieves all workers")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Workers retrieved successfully")
    })
    @GetMapping({"", "/"})
    public List<Trabajador> getAll() {
        return service.getAllTrabajadores();
    }

    @Operation(summary = "Retrieves worker with the id passed by parameter")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Worker retrieved successfully", content =
                    {@Content(mediaType = "application/json", schema = @Schema(implementation = Trabajador.class))}),
            @ApiResponse(responseCode = "404", description = "There is no worker with the specified id", content = {@Content(examples = {
                    @ExampleObject("""
                                {
                                    "error": "No se ha encontrado un trabajador con el id introducido",
                                    "message": "Not in database"
                                }
                            """)
            })}),
            @ApiResponse(responseCode = "400", description = "Error in data passed by parameter", content = {@Content(examples = {
                    @ExampleObject("""
                                {
                                    "error": "Se ha producido un error al formular la petición",
                                    "message": "Bad request"
                                }
                            """)
            })})
    })
    @GetMapping({"/{id}"})
    public ResponseEntity<?> getTrabajadorById(@PathVariable @Parameter(description = "Worker ID", example = "1") String id) {
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

    @Operation(summary = "Adds the worker passed in the body")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Worker added successfully", content =
                    {@Content(mediaType = "application/json", schema = @Schema(implementation = Trabajador.class))}),
            @ApiResponse(responseCode = "400", description = "Error with the data", content = {@Content(examples = {
                    @ExampleObject("""
                                {
                                    "error": "Se ha producido un error durante la inserción",
                                    "message": "El id ha de ser de 5 o menos caracteres"
                                }
                            """)
            })})
    })
    @PostMapping({"", "/"})
    public ResponseEntity<?> postTrabajo(@RequestBody(required = false) @Valid @Parameter(description = "New worker") Trabajador trabajador) {
        Map<String, Object> responseMap = new HashMap<>();
        try {
            return new ResponseEntity<>(service.save(trabajador), HttpStatus.OK);
        } catch (Exception e) {
            responseMap.put("error", "Se ha producido un error de inserción");
            responseMap.put("message", e.getMessage());
            return new ResponseEntity<>(responseMap, HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Edits the worker specified and returns it with its changes made")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Trabajador editado con éxito", content =
                    {@Content(mediaType = "application/json", schema = @Schema(implementation = Trabajador.class))}),
            @ApiResponse(responseCode = "404", description = "No existe un trabajador con el id pasado por parámetro", content = {@Content(examples = {
                    @ExampleObject("""
                                {
                                    "error": "No se ha encontrado un trabajador con el id introducido",
                                    "message": "Not in database"
                                }
                            """)
            })}),
            @ApiResponse(responseCode = "400", description = "Error en los datos pasados en el cuerpo de la petición", content = {@Content(examples = {
                    @ExampleObject("""
                                {
                                    "error": "Se ha producido un error durante la actualización",
                                    "message": "Bad request"
                                }
                            """)
            })})
    })
    @PutMapping({"/{id}"})
    public ResponseEntity<?> putTrabajador(@PathVariable @Parameter(description = "Id of worker to edit", example = "1") String id, @RequestBody(required = false) @Valid Trabajador trabajador) {
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

    @Operation(summary = "Removes the worker with the specified ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Worker removed successfully"),
            @ApiResponse(responseCode = "404", description = "There is no worker with the specified ID", content = {@Content(examples = {
                    @ExampleObject("""
                                {
                                    "error": "No se ha encontrado un trabajador con el id introducido",
                                    "message": "Not in database"
                                }
                            """)
            })}),
            @ApiResponse(responseCode = "400", description = "Error with the request", content = {@Content(examples = {
                    @ExampleObject("""
                                {
                                    "error": "Se ha producido un error durante el borrado",
                                    "message": "Bad request"
                                }
                            """)
            })})
    })
    @DeleteMapping({"/{id}"})
    public ResponseEntity<?> deleteTrabajo(@PathVariable @Parameter(description = "Id of the worker to be removed", example = "1") String id) {
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

    @Operation(summary = "If the id and the password are correct, returns a list with the workers pending jobs")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Correct credentials"),
            @ApiResponse(responseCode = "401", description = "Incorrect credentials", content = {@Content(examples = {
                    @ExampleObject("""
                                {
                                    "error": "Las credenciales introducidas no son correctas",
                                    "message": "Unauthorized"
                                }
                            """)
            })}),
            @ApiResponse(responseCode = "400", description = "Error in the request", content = {@Content(examples = {
                    @ExampleObject("""
                                {
                                    "error": "Se ha producido un error al formular la petición",
                                    "message": "Bad request"
                                }
                            """)
            })})
    })
    @PostMapping({"/login"})
    public Object login(@RequestParam @Parameter(example = "2") String idTrabajador, @RequestHeader("password") @Parameter(example = "password1234") String pass) {
        Map<String, Object> responseMap = new HashMap<>();
        try {
            return service.getTrabajosByTrabajador(idTrabajador, pass);
        } catch (ChangeSetPersister.NotFoundException | EvaluationException e) {
            //Por seguridad, no se dice que no existe un trabajador con el id introducido cuando esto sucede
            responseMap.put("error", "Las credenciales introducidas no son correctas");
            responseMap.put("message", "Not in database");
            return new ResponseEntity<>(responseMap, HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            responseMap.put("error", "Se ha producido un error al formular la petición");
            responseMap.put("message", "Bad request");
            return new ResponseEntity<>(responseMap, HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "If the id and the password are correct, returns a list with the workers done jobs")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Correct credentials"),
            @ApiResponse(responseCode = "401", description = "Incorrect credentials", content = {@Content(examples = {
                    @ExampleObject("""
                                {
                                    "error": "Las credenciales introducidas no son correctas",
                                    "message": "Unauthorized"
                                }
                            """)
            })}),
            @ApiResponse(responseCode = "400", description = "Error in the request", content = {@Content(examples = {
                    @ExampleObject("""
                                {
                                    "error": "Se ha producido un error al formular la petición",
                                    "message": "Bad request"
                                }
                            """)
            })})
    })
    @PostMapping({"/trabajos-finalizados"})
    public Object trabajosFinalizados(@RequestParam @Parameter(example = "2") String idTrabajador, @RequestHeader("password") @Parameter(example = "password1234") String pass) {
        Map<String, Object> responseMap = new HashMap<>();
        try {
            return service.getTrabajosByTrabajadorFinalizados(idTrabajador, pass);
        } catch (ChangeSetPersister.NotFoundException | EvaluationException e) {
            //Por seguridad, no se dice que no existe un trabajador con el id introducido cuando esto sucede
            responseMap.put("error", "Las credenciales introducidas no son correctas");
            responseMap.put("message", "Not in database");
            return new ResponseEntity<>(responseMap, HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            responseMap.put("error", "Se ha producido un error al formular la petición");
            responseMap.put("message", "Bad request");
            return new ResponseEntity<>(responseMap, HttpStatus.BAD_REQUEST);
        }
    }
}