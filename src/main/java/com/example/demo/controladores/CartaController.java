package com.example.demo.controladores;

import com.example.demo.modelos.Carta;
import com.example.demo.repositorio.CartaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/carta")
public class CartaController {

    @Autowired
    private CartaRepository cartaRepository;

    // Mostrar gestión
    @GetMapping("/carta")
    public String gestionCarta(Model model) {
        model.addAttribute("items", cartaRepository.findAll());
        model.addAttribute("tipos", Carta.TipoItem.values());
        model.addAttribute("nuevoItem", new Carta());
        return "carta";
    }

    // Guardar nuevo item
    @PostMapping
    public String guardarItem(@ModelAttribute Carta item) {
        cartaRepository.save(item);
        return "redirect:/admin/carta/carta";
    }

    // Cambiar disponibilidad
    @PostMapping("/toggle/{id}")
    public String toggleDisponibilidad(@PathVariable Long id) {
        cartaRepository.findById(id).ifPresent(item -> {
            item.setDisponible(!item.isDisponible());
            cartaRepository.save(item);
        });
        return "redirect:/admin/carta/carta";
    }

    // 👉 Editar - Mostrar datos en el formulario
    @GetMapping("/editar/{id}")
    public String mostrarEditar(@PathVariable Long id, Model model) {
        Carta item = cartaRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("ID no válido: " + id));
        model.addAttribute("nuevoItem", item);
        model.addAttribute("items", cartaRepository.findAll());
        model.addAttribute("tipos", Carta.TipoItem.values());
        return "carta";
    }

    // 👉 Eliminar
    @GetMapping("/eliminar/{id}")
    public String eliminarItem(@PathVariable Long id) {
        cartaRepository.deleteById(id);
        return "redirect:/admin/carta/carta";
    }
    @GetMapping("/disponibles")
    @ResponseBody
    public List<Carta> obtenerItemsDisponibles() {
        return cartaRepository.findByDisponibleTrue();
    }

}
