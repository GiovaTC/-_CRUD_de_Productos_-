package crudproductos.controller;

import crudproductos.model.Producto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import crudproductos.service.ProductoService;

@Controller
public class ProductoController {

    @Autowired
    ProductoService service;

    @GetMapping("/")
    public String inicio(Model model) {
        model.addAttribute("LISTA", service.listar());

        return "index";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("producto", new Producto());

        return "nuevo";
    }
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Integer id, Model model) {
        model.addAttribute("producto", service.buscar(id));

        return "editar";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Integer id) {
        service.eliminar(id);

        return "redirect:/";
    }
}
