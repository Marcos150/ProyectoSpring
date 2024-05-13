package com.example.proyectospring.controllers.trabajador;

import com.example.proyectospring.entities.trabajador.Trabajador;
import com.example.proyectospring.services.trabajador.impl.TrabajadorService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestControllerAdvice(assignableTypes = {TrabajadorViewController.class, TrabajadorRestController.class})
public class TrabajadorExceptionHandler {
    private final TrabajadorService service;
    private Trabajador trabajador;

    public TrabajadorExceptionHandler(TrabajadorService service) {
        this.service = service;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Object handleInvalidArgument(MethodArgumentNotValidException exception, HttpServletRequest req) {
        if (req.getRequestURI().startsWith("/api/trabajador")) {
            Map<String, String> errorMap = new HashMap<>();
            errorMap.put("message", "model validation failed");
            exception.getBindingResult().getFieldErrors().forEach(error ->
                    errorMap.put(error.getField(), error.getDefaultMessage()));
            exception.getGlobalErrors().forEach(error -> errorMap.put(error.getCode(), error.getDefaultMessage()));
            return errorMap;
        } else if (req.getRequestURI().startsWith("/app/trabajador")) {
            trabajador = new Trabajador();
            Arrays.stream(Trabajador.class.getDeclaredFields()).forEach(field -> {
                try {
                    Arrays.stream(Trabajador.class.getMethods()).forEach(m -> {
                        if (m.getName().matches("set(?i)(" + field.getName() + ")")) {
                            try {
                                if (field.getType().getName().equals(LocalDate.class.getTypeName())) {
                                    m.invoke(trabajador, LocalDate.parse(req.getParameter(field.getName())));
                                } else if (field.getType().getName().equals(BigDecimal.class.getTypeName())) {
                                    m.invoke(trabajador, new BigDecimal(req.getParameter(field.getName())));
                                } else {
                                    m.invoke(trabajador, req.getParameter(field.getName()));
                                }
                            } catch (IllegalAccessException | DateTimeParseException | InvocationTargetException |
                                     IllegalArgumentException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                } catch (IllegalArgumentException | SecurityException e) {
                    e.printStackTrace();
                }
            });
            return discriminateURL(req, exception);
        }
        return null;
    }

    private ModelAndView mvBuilder(String uri, MethodArgumentNotValidException exception) {
        ModelAndView model = new ModelAndView(uri);
        if (uri.contains("index")) model.addObject("trabajadores", service.getAllTrabajadores());
        exception.getBindingResult().getFieldErrors().forEach(result -> model.addObject(result.getField(), result.getDefaultMessage()));
        //Hardcoded
        //exception.getGlobalErrors().forEach(result-> model.addObject("dateRange", result.getDefaultMessage()));
        model.addObject("trabajador", trabajador);
        model.addObject("error", "Error validando el formulario");
        return model;
    }

    private ModelAndView discriminateURL(HttpServletRequest req, MethodArgumentNotValidException exception) {
        Pattern pattern = Pattern.compile("/app/trabajador/put/([A-Z]|[0-9]){1,5}/?", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(req.getRequestURI());
        if (matcher.find()) {
            return mvBuilder("trabajador/detail", exception);
        } else if (req.getRequestURI().startsWith("/app/trabajador/create") ||
                req.getRequestURI().startsWith("/app/trabajador/create/")) {
            return mvBuilder("trabajador/index", exception);
        }
        return mvBuilder("trabajador/detail", exception);
    }
}
