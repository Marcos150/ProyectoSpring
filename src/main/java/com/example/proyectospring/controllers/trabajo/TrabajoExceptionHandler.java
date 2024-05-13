package com.example.proyectospring.controllers.trabajo;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.example.proyectospring.controllers.trabajador.TrabajadorViewController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

import com.example.proyectospring.entities.trabajo.Trabajo;
import com.example.proyectospring.services.trabajo.impl.TrabajoService;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice(assignableTypes = TrabajoViewController.class)
public class TrabajoExceptionHandler {
    @Autowired
    private TrabajoService service;
    private Trabajo job;
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Object handleInvalidArgument(MethodArgumentNotValidException exception, HttpServletRequest req)
    {
        if(req.getRequestURI().startsWith("/api/trabajo")){
            Map<String,String>errorMap=new HashMap<>();
            errorMap.put("message", "model validation failed");
            exception.getBindingResult().getFieldErrors().forEach(error->
            {
                errorMap.put(error.getField(),error.getDefaultMessage());
            });
            exception.getGlobalErrors().forEach(error->{
                errorMap.put(error.getCode(), error.getDefaultMessage());
            });
            return errorMap;
        }
        else if(req.getRequestURI().startsWith("/app/trabajo")){
            job= new Trabajo();
            Arrays.stream(Trabajo.class.getDeclaredFields()).forEach(field->{
                try {
                    Arrays.stream(Trabajo.class.getMethods()).forEach(m->{
                        if(m.getName().matches("set(?i)("+field.getName()+")")){
                            try {
                                if(field.getType().getName().equals(LocalDate.class.getTypeName())){
                                    m.invoke(job, LocalDate.parse(req.getParameter(field.getName())));
                                }
                                else if(field.getType().getName().equals(BigDecimal.class.getTypeName())){
                                    m.invoke(job, new BigDecimal(req.getParameter(field.getName())));
                                }else{
                                    m.invoke(job, req.getParameter(field.getName()));
                                }
                            } catch (IllegalAccessException | DateTimeParseException | InvocationTargetException | IllegalArgumentException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (SecurityException e) {
                    e.printStackTrace();
                }
            });
            return discriminateURL(req,exception);
        }
        return null;
    }

    private ModelAndView mvBuilder(String uri, MethodArgumentNotValidException exception){
        ModelAndView model=new ModelAndView(uri);
        if(uri.contains("index"))model.addObject("trabajos",service.getAllTrabajos());
        exception.getBindingResult().getFieldErrors().forEach(result->{
            model.addObject(result.getField(), result.getDefaultMessage());
        });
        //Hardcoded
        exception.getGlobalErrors().forEach(result->{
            model.addObject("dateRange", result.getDefaultMessage());
        });
        model.addObject("trabajo", job);
        model.addObject("error", "Error validando el formulario");
        return model;
    }
    private ModelAndView discriminateURL(HttpServletRequest req,MethodArgumentNotValidException exception){
        Pattern pattern=Pattern.compile("/app/trabajo/put/([A-Z]|[0-9]){1,5}/?",Pattern.CASE_INSENSITIVE);
        Matcher matcher=pattern.matcher(req.getRequestURI());
        if(matcher.find()){
            return mvBuilder("trabajo/detail.html",exception);
        }
        else if(req.getRequestURI().startsWith("/app/trabajo/create") || 
        req.getRequestURI().startsWith("/app/trabajo/create/")){
            return mvBuilder("trabajo/index.html",exception);
        }
        return mvBuilder("trabajo/detail.html",exception);
    }
}
