package com.example.proyectospring.controllers.trabajo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcProperties.View;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.proyectospring.entities.trabajo.Trabajo;
import com.example.proyectospring.services.trabajo.ITrabajoService;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;


@CrossOrigin(origins = {"*"})
@Controller
@RequestMapping("app/trabajo")
public class TrabajoViewController {
    @Autowired
    private ITrabajoService service;
    @GetMapping({"","/"})
    public String loadIndex(Model model) {
        model.addAttribute("trabajos", service.getAllTrabajos());
        return "/trabajo/index.html";
    }
    @GetMapping({"/{id}","/{id}/"})
    public String loadDetail(@RequestParam String param) {
        return new String();
    }
    
    @RequestMapping({"/create","/create/"})
    public RedirectView create(@Validated Trabajo trabajo) {
        try {
            service.save(trabajo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new RedirectView("/app/trabajo");
    }
    @GetMapping({"/put","/put/"})
    public RedirectView getMethodName(@Validated Trabajo trabajo) {
        try {
            service.save(trabajo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new RedirectView("/app/trabajo");
    }
    @GetMapping({"/delete/{id}","/delete/{id}/"})
    public RedirectView getMethodName(@PathVariable String id) {
        try {
            service.delete(id);
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
        return new RedirectView("/app/trabajo");
    }

    
    
    
}