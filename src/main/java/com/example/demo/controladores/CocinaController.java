package com.example.demo.controladores;

import com.example.demo.modelos.Cocina;
import com.example.demo.servicios.CocinaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate; // Importar SimpMessagingTemplate
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class CocinaController {

    @Autowired
    private CocinaService cocinaService;

    @Autowired
    private SimpMessagingTemplate template; 

    @GetMapping("/mesa1")
    public String mesa1() {
        return "mesa1";
    }
    @GetMapping("/mesa2")
    public String mesa2() {
        return "mesa2";
    }
    @GetMapping("/mesa3")
    public String mesa3() {
        return "mesa3";
    }
    @GetMapping("/mesa4")
    public String mesa4() {
        return "mesa4";
    }
    @GetMapping("/mesa5")
    public String mesa5() {
        return "mesa5";
    }
    @GetMapping("/mesa6")
    public String mesa6() {
        return "mesa6";
    }
    
    

    @GetMapping("/cocina")
    public String mostrarCocina(Model model) {
        List<Cocina> pedidos = cocinaService.listarCocina();
        model.addAttribute("cocina", pedidos);
        return "cocina";
    }

    

    @GetMapping("/mesa/{num}/pedidos")
    @ResponseBody
    public List<Cocina> listarPorMesa(@PathVariable("num") int num) {
        return cocinaService.listarPorMesa(num);
    }

    @PutMapping("/cocina/{id}")
    @ResponseBody
    public Cocina actualizar(@PathVariable int id, @RequestBody Cocina cocina) {
        // Obtener el pedido existente de la base de datos
        Cocina existingCocina = cocinaService.obtenerPedidoPorId(id);
        if (existingCocina != null) {
            // Actualizar solo el campo 'activo' que viene en el request body
            existingCocina.setActivo(cocina.isActivo());
            Cocina updatedCocina = cocinaService.guardarPedido(existingCocina);

            // *** AÑADIR ESTA LÍNEA PARA NOTIFICAR VÍA WEBSOCKET ***
            template.convertAndSend("/topic/estadoCambiado", updatedCocina); // Envía el objeto Cocina completo

            return updatedCocina;
        }
        return null; // O lanzar una excepción si el pedido no se encuentra
    }
    
    @PostMapping("/mesa/vaciarPedidos")
    @ResponseBody
    public String vaciarPedidosDeMesa(@RequestBody int numeroMesa) {
        cocinaService.eliminarPedidosPorMesa(numeroMesa);
        return "Mesa " + numeroMesa + " vaciada correctamente";
    }
}
