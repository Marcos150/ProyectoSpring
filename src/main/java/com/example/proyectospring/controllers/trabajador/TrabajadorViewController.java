package com.example.proyectospring.controllers.trabajador;

import com.example.proyectospring.entities.trabajador.Trabajador;
import com.example.proyectospring.services.trabajador.ITrabajadorService;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@CrossOrigin(origins = {"*"})
@Controller
@RequestMapping("app/trabajador")
public class TrabajadorViewController {
    private final ITrabajadorService service;

    public TrabajadorViewController(ITrabajadorService service) {
        this.service = service;
    }

    @GetMapping({"","/"})
    public String loadIndex(Model model) {
        model.addAttribute("trabajadores", service.getAllTrabajadores());
        return "/trabajador/index";
    }
    @GetMapping({"/{id}","/{id}/"})
    public String loadDetail(@PathVariable String id, Model model) {
        model.addAttribute("trabajador",service.getTrabajadorById(id));
        return "/trabajador/detail";
    }

    @PostMapping({"/create","/create/"})
    public Object create(@Validated Trabajador trabajador) {
        service.save(trabajador);
        return new RedirectView("/app/trabajador");
    }
    @PostMapping({"/put/{id}","/put/{id}/"})
    public Object put(@PathVariable String id,@Validated Trabajador trabajador,Model model) {
        try {
            service.update(id,trabajador);
        } catch (ChangeSetPersister.NotFoundException e) {
            return new RedirectView("/app/trabajador");
        }
        return new RedirectView("/app/trabajador");
    }
    @GetMapping({"/delete/{id}","/delete/{id}/"})
    public RedirectView delete(@PathVariable String id) {
        try {
            service.delete(id);
        } catch (ChangeSetPersister.NotFoundException e) {
            e.printStackTrace();
        }
        return new RedirectView("/app/trabajador");
    }
}
