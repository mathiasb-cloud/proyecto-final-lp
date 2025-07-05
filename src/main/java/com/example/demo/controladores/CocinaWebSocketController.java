package com.example.demo.controladores;

import com.example.demo.modelos.Cocina;
import com.example.demo.servicios.CocinaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;


@Controller
public class CocinaWebSocketController {

    @Autowired
    private CocinaService cocinaService;
    @Autowired
    private SimpMessagingTemplate template;

    @MessageMapping("/enviarPedido")
    @SendTo("/topic/recibirPedido") // Este canal envía el pedido guardado a todos los suscriptores
    public Cocina recibirYGuardar(Cocina cocina) {
        System.out.println("Recibido nuevo pedido: " + cocina.getNombre() + " - Mesa: " + cocina.getNumeroMesa());
        Cocina savedCocina = cocinaService.guardarPedido(cocina);
        return savedCocina; // El objeto guardado (con ID) se envía a /topic/recibirPedido
    }

    @MessageMapping("/eliminarPedido")
    public void eliminarPedidoWebSocket(String idRaw) {
        int id = Integer.parseInt(idRaw);
        cocinaService.eliminarPedido(id);
        template.convertAndSend("/topic/pedidoEliminado", id); // Notifica a todos que se eliminó
    }

    // NUEVO: Manejar la actualización de pedidos desde la mesa (nombre/detalle)
    @MessageMapping("/actualizarPedido")
    public void actualizarPedidoWebSocket(Cocina cocina) {
        System.out.println("Actualización de pedido recibida desde mesa: ID " + cocina.getId() + ", Nombre: " + cocina.getNombre() + ", Detalle: " + cocina.getDetalle());
        Cocina existingCocina = cocinaService.obtenerPedidoPorId(cocina.getId());
        if (existingCocina != null) {
            existingCocina.setNombre(cocina.getNombre());
            existingCocina.setDetalle(cocina.getDetalle());
            // No se actualiza numeroMesa ni activo desde aquí, ya que eso se maneja en otros flujos
            Cocina updatedCocina = cocinaService.guardarPedido(existingCocina);
            // Notificar a todas las vistas (cocina y otras mesas) sobre la actualización
            template.convertAndSend("/topic/recibirPedido", updatedCocina); // Reutilizamos el canal de "recibirPedido" para actualizaciones
        }
    }
}
